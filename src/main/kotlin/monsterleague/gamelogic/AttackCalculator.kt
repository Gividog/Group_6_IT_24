package monsterleague.gamelogic

import kotlin.random.Random

class AttackCalculator (
    val attackingMonster: Monster,
    val defendingMonster: Monster,
    val attack: Attack,
    val battleStats: BattleStats,
) {
    val critChance = 0.04

    fun calculateDamage() : Int {
        val damage = 0
        /*
        val damage = ( (((((2.0 * critical/ 5.0)+ 2.0 ) * attackingMonster.attacks[chosenAttackNumber].attackSpecificData.power * attackingMonster.stats.attack / attackingMonster.stats.defense) / 50.0) + 2.0) * stabFactor[chosenAttackNumber] * typeEffectiveness *  randomNumber )
        calculateLeftAmountOfAttack()
        */

        return damage
    }

    fun calculateEfficiency() : Int {
        val dummy = 0
        return dummy
    }

    fun calculateCriticalStrike(damage : Int) : Int {
        return if (Random.nextDouble() < critChance) {
            damage * 2
        } else {
            damage
        }
    }


}