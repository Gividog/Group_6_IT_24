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

  fun surrender(surrenderingTrainer: TrainerInBattle) {
    winner = if (surrenderingTrainer == trainerOne) {
      trainerTwo
    } else {
      trainerOne
    }
  }

  fun startNextRound() {
    round++
    trainerOne.setNotReadyToFight()
    trainerTwo.setNotReadyToFight()
  }

  fun endRound() {
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

  fun sortActiveMonstersByInitiative(): List<Monster> {
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
    val attacker = choice.attackingMonster
    val defender = choice.targetedMonster
    val attack = choice.selectedAttack
    val hitChanceCalculator = HitChanceCalculator(attack)

    handleAttackKind(choice)
  }

  private fun handleAttackKind(choice: TrainerChoice.AttackChoice) {
    val attacker = choice.attackingMonster
    val defender = choice.targetedMonster
    val attack = choice.selectedAttack

    when (getKindOfAttack(choice.selectedAttack)) {
      AttackKinds.SPECIAL, AttackKinds.PHYSICAL -> handleAttack(
        attack,
        attacker,
        defender
      )

      AttackKinds.BUFF, AttackKinds.DEBUFF -> applyStatChanges(
        attack,
        attacker,
        defender
      )

      // AttackKinds.STATUS -> applyStatus(attack, defender)
    }
  }

  fun handleAttack(attack: Attack, attacker: Monster, defender: Monster) {
    val attackerStats = battleStats[attacker] ?: throw IllegalArgumentException(
      "Attacker stats not found"
    )
    val defenderStats = battleStats[defender] ?: throw IllegalArgumentException(
      "Defender stats not found"
    )

    val hitChanceCalculator = HitChanceCalculator(attack)

    if (hitChanceCalculator.willHit()) {
      val damageCalculator = DamageCalculator(
        attackingMonster = attacker,
        defendingMonster = defender,
        attack = attack
      )

      val damage = if (simpleDamageCalculation) {
        damageCalculator.simpleDamageCalculation()
      } else {
        damageCalculator.calculateDamage()
      }
      defender.takeDamage(damage)
      applyStatChanges(attack, defender, attacker)
    }
  }

  fun switchActiveMonster(
    trainer: TrainerInBattle,
    choice: TrainerChoice.SwitchChoice,
  ) {
    val inMonster = choice.inMonster
    val monsters = trainer.getMonsters()

    if (inMonster in monsters) trainer.setActiveMonster(inMonster)
    trainer.setReadyToFight()
  }

  fun healActiveMonster(
    trainer: TrainerInBattle,
    choice: TrainerChoice.HealChoice,
  ) {
    val healsRemaining = trainer.getHealsRemaining()
    val activeMonster = trainer.getActiveMonster()

    if (healsRemaining > 0) {
      activeMonster.heal()
      trainer.setHealsRemaining(healsRemaining - 1)

      trainer.setReadyToFight()
    }
  }

  fun getKindOfAttack(attack: Attack): AttackKinds {
    return attack.kind
  }

  fun determineWinner() {
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
}
