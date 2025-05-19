package monsterleague.gamelogic

class Battle(
    val battleID: Int,
    var round: Int,
    var winner: String?,
    val trainers: List<Trainer>
) {
    fun handleSurrender(surrenderingTrainer: Trainer) {
        val opponent = trainers.first { it != surrenderingTrainer }
        winner = opponent.name
        println("${surrenderingTrainer.name} has surrendered. ${opponent.name} wins!")
    }

    fun startNextRound() {
        if (winner != null) {
            println("The battle is already over. Winner: $winner")
            return
        }

        trainers.forEach { trainer ->
            if (trainer.activeMonster.BattleStats.hp <= 0) {
                val aliveMonsters = trainer.monsters.filter { it.BattleStats.hp > 0 }
                println(aliveMonsters)
                if (aliveMonsters.isNotEmpty()) {
                    trainer.switchActiveMonster(aliveMonsters.first())
                } else {
                    handleSurrender(trainer)
                    return
                }
            }
        }

        round++
        println("===== Starting Round $round =====")
        println("All trainers are ready for Round $round.")
    }

    fun determineWinner(trainer1 : Trainer, trainer2 : Trainer) : Trainer {
        val winner = trainer1

        for (monster in trainer1.monsters) {
            if (monster.BattleStats.hp > 0) {
                break
            } else {
                val winner = trainer2
            }
        }

        for (monster in trainer2.monsters) {
            if (monster.BattleStats.hp > 0) {
                break
            } else {
                val winner = trainer1
            }
        }
        return winner
    }

    fun proofIfBattleIsFinished():Boolean{
        return trainers[0].monsters.isEmpty() || trainers[1].monsters.isEmpty()

    }

}
