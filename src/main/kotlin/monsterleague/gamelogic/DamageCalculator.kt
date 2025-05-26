package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.BuffAttack
import kotlin.random.Random

class DamageCalculator () {


    private val criticalChance = 0.04

   fun calculateEfficiency(attackingMonster : Monster, defendingMonster : Monster) : Double {
        val typeTable = TypeTable()
        val attackingMonsterType = attackingMonster.type
        val defendingMonsterType = defendingMonster.type

        return when{
            typeTable.efficiencyOf(attackingMonsterType).contains(defendingMonsterType) -> 2.0
            typeTable.inefficienciesOf(attackingMonsterType).contains(defendingMonsterType) -> 0.5
            else -> 1.0
        }
    }

     fun calculateCriticalStrike(randomSupplier: () -> Double = {Random.nextDouble()}) : Double {
        return if (randomSupplier() < criticalChance) {
            2.0
        } else {
            1.0
        }
    }

    fun calculateDamage(defendingMonster: Monster, attackingMonster: Monster, attack: Attack){

    }


}