package hwr.oop.monsterleague.gamelogic.calculators

import hwr.oop.monsterleague.gamelogic.Monster
import hwr.oop.monsterleague.gamelogic.TypeTable

class EfficiencyCalculator(
  private val attackingMonster: Monster,
  private val defendingMonster: Monster,
) {

  fun calculateEfficiency(
  ): Double {
    val typeTable = TypeTable()
    val attackingMonsterType = attackingMonster.getType()
    val defendingMonsterType = defendingMonster.getType()

    return when {
      typeTable.efficiencyOf(attackingMonsterType)
        .contains(defendingMonsterType) -> 2.0

      typeTable.inefficiencyOf(attackingMonsterType)
        .contains(defendingMonsterType) -> 0.5

      else -> 1.0
    }
  }
}