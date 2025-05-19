package monsterleague.gamelogic

class Battle(
    private val battleID: Int,
    var round: Int,
    var winner : Trainer?,
    val trainers: List<Trainer>,
    val monster : Monster
) {
    fun handleSurrender(surrenderingTrainer: Trainer) {
        val opponent = trainers.first { it != surrenderingTrainer }
        winner = opponent.name
        println("${surrenderingTrainer.name} has surrendered. ${opponent.name} wins!")
    }

    fun startNextRound() {
        // TODO : Check, ob beide Trainer eine Aktion ausgewählt haben
    }
}
