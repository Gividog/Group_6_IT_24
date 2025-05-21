package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack

class Monster(
    val name: String,
    val type: Type,
    val baseStats: BaseStats,
    var battleStats: BattleStats,
    val attacks: List<Attack>,
) {
    fun healMonster() {
        val healingPercentage = 0.3

        val maxHP = baseStats.getHP()
        val currentHP = battleStats.getHP()

        val healingAmount = (maxHP * healingPercentage).toInt()
        battleStats.hp = minOf(currentHP + healingAmount, maxHP)
    }

    fun deadMonster(): Boolean {
        return battleStats.hp <= 0
    }
}