package monsterleague.gamelogic

import monsterleague.gamelogic.Battle

class Trainer(
    val name: String,
    var monsters: List<Monster>,
    var activeMonster: Monster,
    var healsRemaining: Int
) {
    val healingPercentage = 0.3 //30%

    fun chooseAttack(attackIndex: Int) : Attack{
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
        val monster = activeMonster

        //limit the amount of heals a trainer has in a fight
        if (healsRemaining <= 0) {
            println("$name has no heals left!")
            return
        }

        val maxHP = monster.BaseStats.hp
        val currentHP = monster.BattleStats.hp

        val healAmount = (maxHP * healingPercentage).toInt()
        val newHP = minOf(currentHP + healAmount, maxHP)

        monster.BattleStats.hp = newHP
        healsRemaining--

        println("${monster.name} was healed by $healAmount HP!")
        println("$name has $healsRemaining heals left.")
    }

    fun surrender(battle: Battle) {
        battle.handleSurrender(this)
    }
}
