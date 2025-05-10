package monsterleague.gamelogic

class AttackCalculator (
    val attackingMonster: Monster,
    val defendingMonster: Monster,
    val attack: Attack,
    val battleStats: BattleStats,
) {

    fun calculateDamage() : Int {

        /*
        val damage = ( (((((2.0 * critical/ 5.0)+ 2.0 ) * attackingMonster.attacks[chosenAttackNumber].attackSpecificData.power * attackingMonster.stats.attack / attackingMonster.stats.defense) / 50.0) + 2.0) * stabFactor[chosenAttackNumber] * typeEffectiveness *  randomNumber )
        calculateLeftAmountOfAttack()
        */

        //return damage
    }

    fun calculateEfficiency() : Int {

    }


}