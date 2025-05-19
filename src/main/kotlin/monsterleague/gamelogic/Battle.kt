package monsterleague.gamelogic

import kotlin.inc

class Battle(
    private val battleID: Int,
    var round: Int,
    var winner : Trainer?,
    val trainers: List<Trainer>,
    val monster : Monster
) {
    fun surrender(surrenderingTrainer: Trainer) {
        val opponent = trainers.first { it != surrenderingTrainer }
        winner = opponent
    }

    fun startNextRound() {
        // TODO : Check, ob beide Trainer eine Aktion ausgewählt haben
    }

    fun proofIfBattleIsFinished():Boolean{
        return trainers[0].monsters.isEmpty() || trainers[1].monsters.isEmpty()

    }

}
