package monsterleague.gamelogic

import hwr.oop.monsterleague.gamelogic.TrainerChoice
import hwr.oop.monsterleague.gamelogic.TrainerInBattle
import hwr.oop.monsterleague.gamelogic.calculators.DamageCalculator
import hwr.oop.monsterleague.gamelogic.calculators.HitChanceCalculator
import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.AttackKinds

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
  private val battleStats = mapOf<Monster, BattleStats>()

  private fun applyStatChanges(
    attack: Attack,
    attacker: Monster,
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
      }
    } else if (trainerTwo.getActiveMonster().defeatedMonster()) {
      val nextAlive =
        trainerTwo.getMonsters().firstOrNull() { m -> !m.defeatedMonster() }
      if (nextAlive != null) {
        trainerTwo.setActiveMonster(nextAlive)
      } else {
        surrender(trainerTwo)
      }
    } else {
      startNextRound()
    }
  }

  private fun sortActiveMonstersByInitiative(): List<Monster> {
    return listOf(trainerOne.getActiveMonster(), trainerTwo.getActiveMonster())
      .sortedByDescending { it.getInitiativeStat() }
  }

  private fun simulateRound() {
    mapOfChoice.entries.sortedByDescending { it.value.precedence() }
      .forEach { (trainer, choice) ->
        when (choice) {  // when is exhaustive, because TrainerChoice is a sealed interface!
          is TrainerChoice.HealChoice -> healActiveMonster(trainer, choice)
          is TrainerChoice.AttackChoice -> trainerChooseAttack(trainer, choice)
          is TrainerChoice.SwitchChoice -> switchActiveMonster(trainer, choice)
        }
      }
    mapOfChoice.clear()
  }

  fun trainerChooseAttack(
    trainer: TrainerInBattle,
    choice: TrainerChoice.AttackChoice,
  ) {
    val attackingMonster = choice.attackingMonster
    val targetedMonster = choice.targetedMonster
    val attack = choice.selectedAttack

    if (attack !in attackingMonster.getAttacks()) {
      throw Exceptions.AttackNotFoundException(attack, attackingMonster)
    } else if (attack.getPowerPoints() == 0) {
      throw Exceptions.AttackCannotBeUsedException(attack, attackingMonster)
    } else if (attackingMonster !== trainer.getActiveMonster()) {
      throw Exceptions.MonsterNotActiveException(attackingMonster, trainer)
    } else if (targetedMonster !== trainer.getActiveMonster()) {
      throw Exceptions.MonsterNotActiveException(targetedMonster, trainer)
    } else
      handleAttackKind(choice)
  }

  private fun handleAttackKind(choice: TrainerChoice.AttackChoice) {
    val attackingMonster = choice.attackingMonster
    val targetedMonster = choice.targetedMonster
    val attack = choice.selectedAttack

    when (getKindOfAttack(choice.selectedAttack)) {
      AttackKinds.SPECIAL, AttackKinds.PHYSICAL -> handleAttack(
        attack,
        attackingMonster,
        targetedMonster,
      )

      AttackKinds.BUFF, AttackKinds.DEBUFF -> applyStatChanges(
        attack,
        attackingMonster,
        targetedMonster
      )

      // AttackKinds.STATUS -> applyStatus(attack, defender)
    }
  }

  private fun handleAttack(
    attack: Attack,
    attackingMonster: Monster,
    targetedMonster: Monster,
  ) {
    val defendingTrainer: TrainerInBattle = getDefendingTrainer()
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
      applyStatChanges(attack, targetedMonster, attackingMonster)
    }

    if (defendingTrainer.checkActiveMonsterDefeated()) {
      // TODO : Trainer muss nächstes, noch nicht besiegtes Monster auswählen (switchActiveMonster()?)
    }
  }

  fun switchActiveMonster(
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

  fun healActiveMonster(
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

  private fun determineWinner() {
    if (trainerOne.getMonsters().all { it.defeatedMonster() }) {
      battleOver = true
      winner = trainerTwo
    } else if (trainerTwo.getMonsters().all { it.defeatedMonster() }) {
      battleOver = true
      winner = trainerOne
    }
  }

  /**
   * Queries
   * */

  fun getKindOfAttack(attack: Attack): AttackKinds {
    return attack.kind
  }

  fun getWinner(): TrainerInBattle? {
    return winner
  }

  fun getRounds(): Int {
    return round
  }

  fun getTrainerOne(): TrainerInBattle {
    return trainerOne
  }

  fun getTrainerTwo(): TrainerInBattle {
    return trainerTwo
  }

  fun getAttackingTrainer(): TrainerInBattle {
    // TODO
    return trainerOne
  }

  fun getDefendingTrainer(): TrainerInBattle {
    // TODO
    return trainerOne
  }

  /**
   * Test Commands
   */

  fun testEndRound() {
    endRound()
  }

  fun testSurrender(trainer: TrainerInBattle) {
   surrender(trainer)
  }

  fun testDetermineWinner() {
    determineWinner()
  }

  fun testSortActiveMonsterByInitiative () : List<Monster> {
    return sortActiveMonstersByInitiative()
  }

  fun testStartNextRound (battle : Battle) {
    startNextRound()
  }

  fun testHandleAttack(
    battle: Battle,
    attack: Attack,
    attackingMonster: Monster,
    targetedMonster: Monster,
  ) {
    battle.handleAttack(attack, attackingMonster, targetedMonster)
  }
}
