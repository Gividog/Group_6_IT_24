package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.AttackKinds
import java.util.UUID

class Battle(
    private val battleID: UUID = UUID.randomUUID(),
    private val trainers: List<Trainer>,
) {
    private var round: Int = 1
    private var winner : Trainer? = null
    private var battleOver : Boolean = false
    private val chosenAttacksMap = mutableMapOf<Monster, Attack>()

    fun surrender(surrenderingTrainer: Trainer) {
        val opponent = trainers.first { it != surrenderingTrainer }
        winner = opponent
    }

    fun startNextRound() {
       round++
       trainers.forEach { it.setNotReadyToFight() }
    }

    fun chooseAttack(
        attackingTrainer: Trainer,
        attack: Attack
    ) {
        val attackingMonster = attackingTrainer.activeMonster

        trainers.forEach{
            chosenAttacksMap[attackingMonster] = it.trainerChooseAttack(attack)
        }
        chosenAttacksMap[attackingMonster] = attack
        if (chosenAttacksMap.size == 2) {
            simulateRound()
        }
    }

    fun endRound() {
        trainers.forEach {
            if (it.activeMonster.deadMonster()){
                val nextAlive = it.monsters.firstOrNull { m -> !m.deadMonster() }
                if (nextAlive != null) {
                    it.activeMonster = nextAlive
                } else {
                    surrender(it)
                }
            }
        }
    }

    fun sortActiveMonstersByInitiative():List<Monster>{
        return listOf(trainers[0].activeMonster, trainers[1].activeMonster)
            .sortedByDescending { it.baseStats.getInitiative()}
    }

    /*
    fun getOpponent(): Monster {
        //return
    }*/

    fun simulateRound() {
        // TODO : check(chosenAttacksMap.size == 2) {
        // TODO : val monstersInOrderOfAttack = sortActiveMonstersByInitiative()
        // TODO : for (attacker in monstersInOrderOfAttack) {
            // TODO : val defender = otherMonster(attacker)
            // TODO : val attack = chosenAttacksMap[attacker]!!
            // TODO : val damage = DamageCalculator.calculateDamage(
                // TODO : attackingMonster = attacker,
                // TODO : defendingMonster = defender,
                // TODO : attack = attack,
            // TODO : )
            // TODO : defender.takeDamage(damage)
        // TODO : }
        // TODO : chosenAttacksMap.clear()

        // TODO : }
    }

    /*fun attackingMonster(attacking: Trainer) =
        if (attacking == red) activeMonsterOfRed else activeMonsterOfBlue

    fun defendingMonster(attacking: Trainer) =
        if (attacking == red) activeMonsterOfBlue else activeMonsterOfRed*/


    fun getKindOfAttack(attack: Attack): AttackKinds {
        return attack.kind
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
