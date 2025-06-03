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
) {
  private var round: Int = 1
  private var winner: TrainerInBattle? = null
  private var battleOver: Boolean = false
  private val mapOfChoice = mutableMapOf<TrainerInBattle, TrainerChoice>()
  private val mapOfAttacks = mutableMapOf<Monster, Attack>()

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

  fun chooseAttack(
    attackingTrainer: TrainerInBattle,
    attack: Attack,
  ) {
    val attackingMonster = attackingTrainer.getActiveMonster()

    mapOfAttacks[attackingMonster] = trainerOne.trainerChooseAttack(attack)
    mapOfAttacks[attackingMonster] = trainerTwo.trainerChooseAttack(attack)

    if (mapOfChoice.size == 2) {
      simulateRound()
    }
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
    val monstersInOrderOfAttack = sortActiveMonstersByInitiative()

    for (attacker in monstersInOrderOfAttack) {
      val defender = otherMonster(attacker)
      val attack = mapOfAttacks[attacker]!!
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
    mapOfChoice.clear()
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
