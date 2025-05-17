package monsterleague.gamelogic

class Battle(
    val battleID: Int,
    var round: Int,
    var winner: String?,
    val trainers: List<Trainer>
) {
    //TODO : endTurn() / confirmTurn()
    //TODO : startNextRound() -> Nikita
    //TODO : endBattle() -> Liesa
    //TODO : determineWinner() -> Amy

    fun handleSurrender(surrenderingTrainer: Trainer) {
        val opponent = trainers.first { it != surrenderingTrainer }
        winner = opponent.name
        println("${surrenderingTrainer.name} has surrendered. ${opponent.name} wins!")
    }
}
