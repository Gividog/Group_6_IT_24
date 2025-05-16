package monsterleague.gamelogic

class Battle(
    val battleID: Int,
    var round: Int,
    var winner: String?,
    val trainers: List<Trainer>
) {
    //TODO : endTurn() / confirmTurn() -> Erik
    //TODO : startNextRound() -> Nikita
    //TODO : endBattle() -> Liesa
    //TODO : determineWinner() -> Amy
    //TODO : surrender()
}
