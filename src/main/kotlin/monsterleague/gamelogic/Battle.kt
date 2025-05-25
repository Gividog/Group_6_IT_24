package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.BuffAttack
import java.util.UUID
import kotlin.inc

class Battle(
    private val battleID: UUID = UUID.randomUUID(),
    private val trainers: List<Trainer>
) {
    private var round: Int = 1
    private var winner : Trainer? = null
    private var battleOver : Boolean = false

    fun surrender(surrenderingTrainer: Trainer) { // Test erledigt
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

    /**
     * Messages
     * */

    fun getWinner() : Trainer? {
        return winner
    }

    fun getRounds() : Int {
        return round
    }


}
