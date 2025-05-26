package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack

class Monster(
    val name: String,
    val type: Type,
    val baseStats: BaseStats,
    var battleStats: BattleStats,
    val attacks: List<Attack>,
) {
    fun heal() {
        val healingPercentage = 0.3

        val maxHP = baseStats.getHP()
        val currentHP = battleStats.getHP()

        val healAmount = (maxHP * healingPercentage).toInt()
        battleStats.updateHP(minOf(currentHP + healAmount, maxHP))
    }

    fun deadMonster(): Boolean {
        return battleStats.getHP() <= 0
    }
}