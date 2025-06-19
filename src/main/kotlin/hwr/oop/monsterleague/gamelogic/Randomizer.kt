package hwr.oop.monsterleague.gamelogic

import kotlin.random.Random

class Randomizer(
  seed: Int = System.nanoTime().toInt(),
) {
  private val random = Random(seed)
  private val criticalChance = 0.04

  private fun randomNumber(): Double {
    val randomNumber = (85..100).random()
    val randomFactorForDamageCalculation = randomNumber / 100.0
    return randomFactorForDamageCalculation
  }

  private fun nextDouble(): Double {
    return random.nextDouble()
  }

  private fun calculateCriticalStrike(random: Double = nextDouble()): Double {
    return if(random < criticalChance) 2.0 else 1.0
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

  fun getCriticalStrike(random: Double = nextDouble()): Double {
    return calculateCriticalStrike(random)
  }

  fun getHitChance():Int{
    return hitChance()
  }
}