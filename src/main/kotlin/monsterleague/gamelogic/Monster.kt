package monsterleague.gamelogic

class Monster(
    val name: String,
    val type: Type,
    var status: Int,
    val BaseStats: Stats,
    var BattleStats: Stats,
    val attacks: List<Attack>,
) {
}