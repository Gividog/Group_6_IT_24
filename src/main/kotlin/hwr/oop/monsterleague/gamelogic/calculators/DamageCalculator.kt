package hwr.oop.monsterleague.gamelogic.calculators

import hwr.oop.monsterleague.gamelogic.Randomizer
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.attacks.Attack
import kotlin.math.roundToInt

class DamageCalculator(
  private val attackingMonster: Monster,
  private val defendingMonster: Monster,
  private val attack: Attack,
) {
  private var defenseStat = 0
  private var attackStat = 0

  private fun getStabFactor(): Double {
    return if (attackingMonster.getType() == attack.type) {
      1.5
    } else {
      1.0
    }
  }

  private fun checkForSpecialAttack() {
    if (attack.isSpecial()) {
      defenseStat = defendingMonster.getSpecialDefenseStat()
      attackStat = attackingMonster.getSpecialAttackStat()
    } else if (!attack.isSpecial()) {
      defenseStat = defendingMonster.getDefenseStat()
      attackStat = attackingMonster.getAttackStat()
    }
  }

  fun calculateDamage(
    criticalHit: Double = Randomizer().getCriticalStrike(),
    random: Double = Randomizer().getRandomNumber(),
  ): Int {

    checkForSpecialAttack()

    val stabFactor = getStabFactor()
    val efficiency = EfficiencyCalculator(
      attackingMonster,
      defendingMonster
    ).calculateEfficiency()

    val damage =
      ((((((100 * criticalHit / 5) + 2) * attack.power * (attackStat / defenseStat)) / 50) + 2) * stabFactor * efficiency * random)
    println(damage.toString())
    val damageInt = damage.roundToInt()
    println(damageInt.toString())
    return damageInt
  }

  fun simpleDamageCalculation(): Int {
    checkForSpecialAttack()

    val stabFactor = getStabFactor()
    val efficiency = EfficiencyCalculator(
      attackingMonster,
      defendingMonster
    ).calculateEfficiency()

    val damage =
      ((((((100 / 5) + 2) * attack.power * (attackStat / defenseStat)) / 50) + 2) * stabFactor * efficiency)
    println(damage.toString())
    val damageInt = damage.roundToInt()
    println(damageInt.toString())
    return damageInt
  }

}