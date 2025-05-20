package monsterleague.gamelogic

import java.util.UUID
import kotlin.inc

class Battle(
    private val battleID: UUID,
    var round: Int,
    private var winner : Trainer?,
    private val trainers: List<Trainer>
) {

    private fun generateBattleID(){
        val battleUuid = UUID.randomUUID()
    }

    fun surrender(surrenderingTrainer: Trainer) {
        val opponent = trainers.first { it != surrenderingTrainer }
        winner = opponent
    }

    fun startNextRound() {
       round++
       trainers.forEach { it.setNotReadyToFight() }
    }

    fun determineWinner() : Trainer? {
        return when{
            trainers[0].monsters.all { it.deadMonster() } -> trainers[1]
            trainers[1].monsters.all { it.deadMonster() } -> trainers[0]
            else -> null
        }

    }

}
