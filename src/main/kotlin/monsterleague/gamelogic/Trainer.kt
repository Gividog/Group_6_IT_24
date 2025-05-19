package monsterleague.gamelogic

import monsterleague.gamelogic.Battle
import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.PhysicalAttack

class Trainer(
    val name: String,
    var monsters: List<Monster>,
    var activeMonster: Monster = monsters.first(), // nach allen Monstern filtern, die nicht tot sind und davon das Erste
    var healsRemaining: Int
) {
    val healingPercentage = 0.3 //30%

    fun chooseAttack(attackIndex: Int) : Attack {
        val attackingMonster = activeMonster
        val attack = attackingMonster.attacks[attackIndex - 1]

        return attack
    }

    fun switchActiveMonster(monster: Monster){
        if (monster in monsters) {
            activeMonster = monster
        }
    }

    fun healActiveMonster() {
        if (healsRemaining <= 0) {
            return
        }
        activeMonster.heal()
        healsRemaining--
    }

    fun determineWinner() : Boolean {
        //TODO
        return true
    }

    fun checkActiveMonsterDead():Boolean {
        return activeMonster.deadMonster()
    }
}
