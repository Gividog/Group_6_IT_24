package monsterleague.gamelogic

import kotlin.random.Random

class DamageCalculator () {
   private val critChance = 0.04

   private fun calculateEfficiency(attackingMonster : Monster, defendingMonster : Monster) : Double {
        val typeTable = TypeTable()
        val attackingMonsterType = attackingMonster.type
        val defendingMonsterType = defendingMonster.type

        return when{
            typeTable.efficiencyOf(attackingMonsterType).contains(defendingMonsterType) -> 2.0
            typeTable.inefficienciesOf(attackingMonsterType).contains(defendingMonsterType) -> 0.5
            else -> 1.0
        }
    }

    private fun calculateCriticalStrike(randomSupplier: () -> Double = {Random.nextDouble()}) : Double {
        return if (randomSupplier() < critChance) {
            2.0
        } else {
            1.0
        }
    }
}