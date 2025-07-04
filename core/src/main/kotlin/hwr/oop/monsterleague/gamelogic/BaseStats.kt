package hwr.oop.monsterleague.gamelogic

import kotlin.contracts.Effect

data class BaseStats(
  private var healthPoints: Int,
  private var initiative: Int,
  private var attack: Int,
  private var defense: Int,
  private var specialDefense: Int,
  private var specialAttack: Int,
) {

  /**
   * Queries
   */

  fun getHealthPoints(): Int {
    return healthPoints
  }
  fun getInitiative(): Int {
    return initiative
  }
  fun getAttack(): Int {
    return attack
  }
  fun getDefense(): Int {
    return defense
  }
  fun getSpecialDefense(): Int {
    return specialDefense
  }
  fun getSpecialAttack(): Int {
    return specialAttack
  }
}