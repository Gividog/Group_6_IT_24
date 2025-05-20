package monsterleague.gamelogic

import java.util.UUID
import kotlin.inc

class Battle(
    private val battleID: UUID,
    private val trainers: List<Trainer>
) {
    private var round: Int = 0
    private var winner : Trainer? = null
    private var battleOver : Boolean = false

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

    fun simulateRound() {

    }

    fun determineWinner(){
        if (trainers[0].monsters.all { it.deadMonster()}) {
            battleOver = true
            winner = trainers[1]

        } else if (trainers[1].monsters.all { it.deadMonster()}) {
            battleOver = true
            winner = trainers[0]
        }
    }

    fun getWinner() : Trainer? {
        return winner
    }


}
