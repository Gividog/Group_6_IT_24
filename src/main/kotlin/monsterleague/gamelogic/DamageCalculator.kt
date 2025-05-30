package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.AttackKinds
import kotlin.math.roundToInt
import kotlin.random.Random

class DamageCalculator(
  private val attackingMonster: Monster,
  private val defendingMonster: Monster,
  private val attack: Attack,
) {

  private val criticalChance = 0.04

  fun calculateEfficiency(
  ): Double {
    val typeTable = TypeTable()
    val attackingMonsterType = attackingMonster.type
    val defendingMonsterType = defendingMonster.type

    return when {
      typeTable.efficiencyOf(attackingMonsterType)
        .contains(defendingMonsterType) -> 2.0

      typeTable.inefficienciesOf(attackingMonsterType)
        .contains(defendingMonsterType) -> 0.5

      else -> 1.0
    }
  }

  private fun getStabFactor(): Double {
    return if (attackingMonster.type == attack.type) {
      1.5
    } else {
      1.0
    }
  }

  private fun randomNumber(): Double {
    val randomNumber = (85..100).random()
    val randomFactorForDamageCalculation = randomNumber / 100.0
    return randomFactorForDamageCalculation
  }

  fun calculateCriticalStrike(randomSupplier: () -> Double = { Random.nextDouble() }): Double {
    return if (randomSupplier() < criticalChance) {
      2.0
    } else {
      1.0
    }
  }

  fun calculateDamage(
    criticalHit: Double = calculateCriticalStrike(),
    random: Double = randomNumber(),
  ): Int {
    var defenseStat = defendingMonster.battleStats.getDefense()
    var attackStat = attackingMonster.battleStats.getAttack()

    if (attack.kind == AttackKinds.SPECIAL) {
      defenseStat = defendingMonster.battleStats.getSpecialDefense()
      attackStat = attackingMonster.battleStats.getSpecialAttack()
    }

    val stabFactor = getStabFactor()
    val efficiency = calculateEfficiency()

    val damage =
      ((((((20 * criticalHit / 5) + 2) * attack.power * (attackStat / defenseStat)) / 50) + 2) * stabFactor * efficiency * random)
    println(damage.toString())
    val damageInt = damage.roundToInt()
    println(damageInt.toString())
    return damageInt
  }

}