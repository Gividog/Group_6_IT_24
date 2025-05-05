package monsterleague.gamelogic

class Battle(
    val battleID: Int,
    var battleStatus: Boolean,
    var round: Int,
    var winner: String?,
    val trainers: List<Trainer>
) {
}