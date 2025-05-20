package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack

class Trainer(
    val name: String,
    var monsters: List<Monster>,
    var activeMonster: Monster,
    var healsRemaining: Int
) {
    val healingPercentage = 0.3 //30%

    fun chooseAttack(attackIndex: Int) : Attack {
        val attackingMonster = activeMonster
        val attack = attackingMonster.attacks[attackIndex - 1]

        return attack
    }

    fun switchActiveMonster(monster: Monster){
        if (monster in monsters) activeMonster = monster
    }

    fun healActiveMonster() {
        if (healsRemaining <= 0) return

        activeMonster.heal()
        healsRemaining--
    }

    fun getReadyToFight(): Boolean {
        return readyToFight
    }

    fun setNotReadyToFight() {
        readyToFight = false
    }

    fun checkActiveMonsterDead():Boolean {
        return activeMonster.deadMonster()
    }
}
