package hwr.oop.monsterleague.gamelogic

import kotlin.random.Random

object Randomizer {
  private const val CRITICAL_CHANCE = 0.04

  private fun randomNumber(): Double {
    val randomNumber = (85..100).random()
    val randomFactorForDamageCalculation = randomNumber / 100.0
    return randomFactorForDamageCalculation
  }

  private fun calculateCriticalStrike(random: Double = Random.nextDouble()): Double {
   return if(random < CRITICAL_CHANCE) 2.0 else 1.0
  }

  private fun hitChance(): Int {
    val chance = (0..100).random()
    return chance
  }

  /**
   * Queries
   */

  fun getRandomNumber():Double{
    return randomNumber()
  }

  fun getCriticalStrike(random: Double = Random.nextDouble()): Double {
    return calculateCriticalStrike(random)
  }

  fun getHitChance():Int{
    return hitChance()
  }
}