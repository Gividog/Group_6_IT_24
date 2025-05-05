package monsterleague.gamelogic

class Monster(
    val name: String,
    var status: Int,
    var baseStats: BaseStats,
    var battleStats: BattleStats,
    val attacks: List<Attack>,
    val type: Type
) {
}