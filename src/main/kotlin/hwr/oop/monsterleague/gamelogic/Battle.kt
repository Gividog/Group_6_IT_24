package monsterleague.gamelogic

import hwr.oop.monsterleague.gamelogic.TrainerChoice
import hwr.oop.monsterleague.gamelogic.TrainerInBattle
import hwr.oop.monsterleague.gamelogic.calculators.DamageCalculator
import hwr.oop.monsterleague.gamelogic.calculators.HitChanceCalculator
import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.AttackKinds
import java.util.UUID
import kotlin.text.get

class Battle(
  private val battleID: UUID = UUID.randomUUID(),
  private val trainerOne: TrainerInBattle,
  private val trainerTwo: TrainerInBattle,
) {
  private var round: Int = 1
  private var winner: TrainerInBattle? = null
  private var battleOver: Boolean = false
  private val mapOfChoice = mutableMapOf<TrainerInBattle, TrainerChoice>()

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
    // return Battle ?
    trainer: TrainerInBattle,
    choice: TrainerChoice.AttackChoice,
  ) {
    val attacker = choice.attackingMonster
    val defender = choice.targetedMonster
    val attack = choice.selectedAttack
    val hitChanceCalculator = HitChanceCalculator(attack)

    if (getKindOfAttack(attack) == AttackKinds.SPECIAL || getKindOfAttack(attack) == AttackKinds.PHYSICAL
    ) if (hitChanceCalculator.willHit()) {
      val damageCalculator = DamageCalculator(
        attackingMonster = attacker,
        defendingMonster = defender,
        attack = attack
      )
      val damage = damageCalculator.calculateDamage()
      defender.takeDamage(damage)

      // Buffs und Debuffs in separate Funktion auslagern
    } else if (getKindOfAttack(attack) == AttackKinds.BUFF) {
      if (hitChanceCalculator.willHit()) {
        // TODO Buff Calculation
      }
    } else if (getKindOfAttack(attack) == AttackKinds.DEBUFF) {
      if (hitChanceCalculator.willHit()) {
        // TODO DeBuff Calculation
      }
    } else if (getKindOfAttack(attack) == AttackKinds.STATUS) {
      if (hitChanceCalculator.willHit()) {
        // TODO Status Calculation
      }
    }
  }

  fun switchActiveMonster(
    // return Battle ?
    trainer: TrainerInBattle,
    choice: TrainerChoice.SwitchChoice,
  ) {
    val inMonster = choice.inMonster
    val outMonster = choice.outMonster
    val monsters = trainer.getMonsters()
    var activeMonster = trainer.getActiveMonster()
    var readyToFight = trainer.getReadyToFight()

    if (inMonster in monsters) activeMonster = inMonster
    readyToFight = true
  }

  fun healActiveMonster(
    // return Battle ?
    trainer: TrainerInBattle,
    choice: TrainerChoice.HealChoice,
  ) {
    var healsRemaining = trainer.getHealsRemaining()
    val activeMonster = trainer.getActiveMonster()
    var readyToFight = trainer.getReadyToFight()

    if (healsRemaining > 0) {
      activeMonster.heal()
      healsRemaining--

      readyToFight = true
    }
  }

  private fun otherMonster(attackingMonster: Monster) =
    if (attackingMonster == trainerOne.getActiveMonster()) trainerTwo.getActiveMonster() else trainerOne.getActiveMonster()

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

  fun getChoiceOfTrainerMap(): Map<TrainerInBattle, TrainerChoice> {
    return mapOfChoice
  }

  fun getTrainerOne(): TrainerInBattle {
    return trainerOne
  }

  fun getTrainerTwo(): TrainerInBattle {
    return trainerTwo
  }

}
