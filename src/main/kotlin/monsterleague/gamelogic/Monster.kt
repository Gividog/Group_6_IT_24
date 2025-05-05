package monsterleague.gamelogic

class Monster(
    val name: String,
    val type: Type,
    var status: Int,
    var baseStats: BaseStats,
    var battleStats: BattleStats,
    val attacks: List<Attack>,
) {
}