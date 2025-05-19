package monsterleague.gamelogic

import monsterleague.gamelogic.Battle
import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.PhysicalAttack

class Trainer(
    val name: String,
    var monsters: List<Monster>,
    var activeMonster: Monster,
    var healsRemaining: Int
) {
    val healingPercentage = 0.3 //30%

    fun chooseAttack(attackIndex: Int) : PhysicalAttack {
        val attackingMonster = activeMonster
        val attack = attackingMonster.attacks[attackIndex - 1]

        return attack
    }

    fun switchActiveMonster(monster: Monster){
        if (monster in monsters) {
            activeMonster = monster
            println("${activeMonster.name} is now active.")
        } else {
            println("${monster.name} doesn't belong to $name")
        }
    }

    fun healActiveMonster() {
        if (healsRemaining <= 0) {
            return
        }
        activeMonster.heal()
        healsRemaining--
        println("${activeMonster.name} was healed to $healsRemaining %.")
    }

    fun determineWinner() : Boolean {
        //TODO
        return true
    }



    fun checkActiveMonsterDead():Boolean {
        return activeMonster.deadMonster()
    }
}
