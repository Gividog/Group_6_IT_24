package monsterleague.gamelogic

import kotlin.random.Random

class AttackCalculator (
    val attackingMonster: Monster,
    val defendingMonster: Monster,
    val attack: Attack,
    val battleStats: Stats,
) {
    private val critChance = 0.04

    fun calculateDamage(attackIndex : Int) : Double {
        val crit = calculateCriticalStrike()
        val efficiency = calculateEfficiency()
        val randomVal = Random.nextDouble()

        val damage = ((((((2.0 * crit / 5.0) + 2.0) * attackingMonster.attacks[attackIndex].physicalAttack.power * attackingMonster.Stats.attack / defendingMonster.Stats.defense) / 50.0) + 2.0) * efficiency * randomVal)
        // calculateLeftAmountOfAttack()

        return damage
    }

    fun calculateEfficiency() : Int {
        val dummy = 0
        return dummy
    }

    private fun calculateCriticalStrike() : Double {
        return if (Random.nextDouble() < critChance) {
            2.0
        } else {
            1.0
        }
    }


}