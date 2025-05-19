package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.PhysicalAttack

class Monster(
    val name: String,
    val type: Type,
    var status: Int,
    val BaseStats: Stats,
    var BattleStats: Stats,
    val attacks: List<Attack>,
) {
    fun heal() {
        val healingPercentage = 0.3

        val maxHP = baseStats.getHP()
        val currentHP = battleStats.getHP()

        val healAmount = (maxHP * healingPercentage).toInt()
        battleStats.hp = minOf(currentHP + healAmount, maxHP)
    }

    fun deadMonster(): Boolean {
        return battleStats.hp <= 0
    }
}