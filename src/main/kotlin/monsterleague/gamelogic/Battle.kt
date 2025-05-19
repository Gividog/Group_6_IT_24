package monsterleague.gamelogic

import java.util.UUID
import kotlin.inc

class Battle(
    private val battleID: UUID,
    var round: Int,
    private var winner : Trainer?,
    private val trainers: List<Trainer>
) {
    fun surrender(surrenderingTrainer: Trainer) {
        val opponent = trainers.first { it != surrenderingTrainer }
        winner = opponent
    }

    fun startNextRound() {
        // TODO : Check, ob beide Trainer eine Aktion ausgewählt haben
    }

    fun proofIfBattleIsFinished():Boolean{
        return trainers[0].monsters.all{it.battleStats.hp == 0} || trainers[1].monsters.all{it.battleStats.hp == 0}
    }

}
