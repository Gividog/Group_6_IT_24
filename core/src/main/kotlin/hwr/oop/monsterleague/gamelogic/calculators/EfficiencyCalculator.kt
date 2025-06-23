package hwr.oop.monsterleague.gamelogic.calculators

import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.TypeTable

class EfficiencyCalculator(
  val attackingMonster: Monster,
  val defendingMonster: Monster,
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