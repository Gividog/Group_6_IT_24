package hwr.oop.monsterleague.gamelogic

import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice
import hwr.oop.monsterleague.gamelogic.trainers.TrainerInBattle
import hwr.oop.monsterleague.gamelogic.calculators.DamageCalculator
import hwr.oop.monsterleague.gamelogic.calculators.HitChanceCalculator
import hwr.oop.monsterleague.gamelogic.attacks.Attack
import hwr.oop.monsterleague.gamelogic.attacks.AttackKinds

import java.util.UUID

class Battle(
  private val battleID: UUID = UUID.randomUUID(),
  private val trainerOne: TrainerInBattle,
  private val trainerTwo: TrainerInBattle,
  private val simpleDamageCalculation: Boolean,
) {
  private var round: Int = 1
  private var winner: TrainerInBattle? = null
  private var battleOver: Boolean = false
  private val mapOfChoice = mutableMapOf<TrainerInBattle, TrainerChoice>()

  private fun applyStatChanges(
    attacker: Monster,
    attack: Attack,
    defender: Monster,
  ) {
    attack.defenderStatChange()?.let { defenderStatChange ->
      defender.getBattleStats().applyChange(defenderStatChange, defender)
    }
    attack.attackerStatChange()?.let { attackerStatChange ->
      attacker.getBattleStats().applyChange(attackerStatChange, attacker)
    }
  }

  private fun surrender(surrenderingTrainer: TrainerInBattle) {
    winner = if (surrenderingTrainer == trainerOne) {
      trainerTwo
    } else {
      trainerOne
    }
  }

  fun submitChoice(
    trainer: TrainerInBattle,
    choice: TrainerChoice,
  ) {
    if (mapOfChoice.containsKey(trainer)) {
      throw IllegalArgumentException("${trainer.getName()} has already submitted a choice.")
    }
    mapOfChoice[trainer] = choice

    if (mapOfChoice.size == 2) {
      simulateRound()
    }
  }

  private fun startNextRound() {
    round++
    trainerOne.setNotReadyToFight()
    trainerTwo.setNotReadyToFight()
  }

  private fun endRound() {
    if (trainerOne.getActiveMonster().defeatedMonster()) {
      val nextAlive =
        trainerOne.getMonsters().firstOrNull() { m -> !m.defeatedMonster() }
      if (nextAlive != null) {
        trainerOne.setActiveMonster(nextAlive)
      } else {
        surrender(trainerOne)
        battleOver = true
        println("${trainerTwo.getName()} won!")
      }
    } else if (trainerTwo.getActiveMonster().defeatedMonster()) {
      val nextAlive =
        trainerTwo.getMonsters().firstOrNull() { m -> !m.defeatedMonster() }
      if (nextAlive != null) {
        trainerTwo.setActiveMonster(nextAlive)
      } else {
        surrender(trainerTwo)
        battleOver = true
        println("${trainerOne.getName()} won!")
      }
    } else {
      startNextRound()
    }
  }

  private fun simulateRound() {
    mapOfChoice.entries.sortedByDescending { it.value.precedence() }
      .forEach { (trainer, choice) ->
        when (choice) {
          is TrainerChoice.HealChoice -> healActiveMonster(trainer, choice)
          is TrainerChoice.AttackChoice -> trainerChooseAttack(trainer, choice)
          is TrainerChoice.SwitchChoice -> switchActiveMonster(trainer, choice)
          is TrainerChoice.SurrenderChoice -> surrender(trainer)
        }
      }

      mapOfChoice.clear()
      endRound()
  }

  private fun trainerChooseAttack(
    trainer: TrainerInBattle,
    choice: TrainerChoice.AttackChoice,
  ) {
    val attackingMonster = choice.attackingMonster
    val targetedMonster = choice.targetedMonster
    val attack = choice.selectedAttack

    if (attack !in attackingMonster.getAttacks()) {
      throw Exceptions.AttackNotFoundException(attack, attackingMonster)
    } else if (attack.powerPoints == 0) {
      throw Exceptions.AttackCannotBeUsedException(attack, attackingMonster)
    } else if (attackingMonster !== trainer.getActiveMonster()) {
      throw Exceptions.MonsterNotActiveException(attackingMonster, trainer)
    } else if (targetedMonster !== getDefendingTrainer(trainer).getActiveMonster()) {
      throw Exceptions.MonsterNotActiveException(targetedMonster, getDefendingTrainer(trainer))
    } else
      handleAttackKind(choice)
  }

  private fun handleAttackKind(choice: TrainerChoice.AttackChoice) {
    val attackingMonster = choice.attackingMonster
    val targetedMonster = choice.targetedMonster
    val attack = choice.selectedAttack

    when (getKindOfAttack(choice.selectedAttack)) {
      AttackKinds.SPECIAL, AttackKinds.PHYSICAL -> handleAttack(
        attackingMonster,
        attack,
        targetedMonster,
      )

      AttackKinds.BUFF, AttackKinds.DEBUFF -> applyStatChanges(
        attackingMonster,
        attack,
        targetedMonster
      )
    }
  }

  private fun handleAttack(
    attackingMonster: Monster,
    attack: Attack,
    targetedMonster: Monster,
  ) {

    val hitChanceCalculator = HitChanceCalculator(attack)

    if (hitChanceCalculator.willHit()) {
      val damageCalculator = DamageCalculator(
        attackingMonster = attackingMonster,
        targetedMonster = targetedMonster,
        attack = attack
      )

      val damage = if (simpleDamageCalculation) {
        damageCalculator.simpleDamageCalculation()
      } else {
        damageCalculator.calculateDamage()
      }
      targetedMonster.takeDamage(damage)
      applyStatChanges(targetedMonster, attack, attackingMonster)
    }

  }

  private fun switchActiveMonster(
    trainer: TrainerInBattle,
    choice: TrainerChoice.SwitchChoice,
  ) {
    val inMonster = choice.inMonster
    val monsters = trainer.getMonsters()

    if (inMonster.getBattleStats().getHP() == 0) {
      throw Exceptions.MonsterDefeatedException(inMonster, trainer)
    } else if (inMonster !in monsters) {
      throw Exceptions.MonsterNotFoundException(trainer, inMonster)
    } else {
      trainer.setActiveMonster(inMonster)
      trainer.setReadyToFight()
    }
  }

  private fun healActiveMonster(
    trainer: TrainerInBattle,
    choice: TrainerChoice.HealChoice,
  ) {
    val healsRemaining = trainer.getHealsRemaining()
    val monsterToBeHealed = choice.monster

    if (healsRemaining == 0) {
      throw Exception("You tried healing your monster but you don't have any heals remaining.")
    } else if (monsterToBeHealed != trainer.getActiveMonster()) {
      throw Exceptions.MonsterNotActiveException(monsterToBeHealed, trainer)
    } else {
      monsterToBeHealed.heal()
      trainer.setHealsRemaining(healsRemaining - 1)
      trainer.setReadyToFight()
    }
  }

  /**
   * Queries
   * */

  fun getBattleID(): UUID {
    return battleID
  }

  fun getKindOfAttack(attack: Attack): AttackKinds {
    return attack.kind
  }

  fun getWinner(): TrainerInBattle? {
    return winner
  }

  fun getTrainerOne(): TrainerInBattle {
    return trainerOne
  }

  fun getTrainerTwo(): TrainerInBattle {
    return trainerTwo
  }

  fun getTrainerByName(name: String): TrainerInBattle {
    return listOf(trainerOne, trainerTwo).first { it.getName() == name }
  }

  fun getDefendingTrainer(trainer: TrainerInBattle): TrainerInBattle {
    return if (trainer == trainerOne) trainerTwo else trainerOne
  }

  fun getSubmittedChoice(trainer: TrainerInBattle): TrainerChoice? {
    return mapOfChoice[trainer]
  }
}
