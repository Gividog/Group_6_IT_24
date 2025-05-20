package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack

class Trainer(
    val name: String,
    var monsters: List<Monster>,
    var activeMonster : Monster,
    var healsRemaining: Int
) {
    private var readyToFight = false

    fun chooseAttack(attackIndex: Int) : Attack {
        val attackingMonster = activeMonster
        val attack = attackingMonster.attacks[attackIndex - 1]

        readyToFight = true
        return attack
    }

    fun switchActiveMonster(monster: Monster){
        if (monster in monsters) activeMonster = monster
        readyToFight = true
    }

    fun healActiveMonster() {
        if (healsRemaining > 0) {
            activeMonster!!.heal()
            healsRemaining--
            println("$activeMonster has been healed. $healsRemaining heals remaining.")
            readyToFight = true
        }
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
