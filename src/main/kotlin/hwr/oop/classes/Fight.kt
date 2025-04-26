package hwr.oop.classes

class Fight(
            val fightID: Int,
            var fightStatus: Boolean,
            val trainers: List<Trainer>,
            var rounds: Int = 0,
            var winner: String = "")
{
    var currentTurn: Int = 0

    fun getCurrentTrainer(): Trainer = trainers[currentTurn]
    fun getCurrentMonster(): Monster? = getCurrentTrainer().activeMonster


    fun startFight() {
        fightStatus = true;
        println("Fight $fightID started!")
    }

    fun chooseAttack(index: Int) {
        val monster = getCurrentMonster()
        if (monster != null) {
            if (index in 1..monster.attacks.size) {
                val attack = monster.attacks[index - 1]
                println("${monster.name} uses ${attack.attackSpecificData.name}!")
                // TODO: CalculateAttack() gets called here when it's done
            } else {
                println("Invalid attack index. Choose a number between 1 and ${monster.attacks.size}.")
            }
        } else {
            println("No active monster found.")
        }
    }

    fun healMonster() {
        val trainer = getCurrentTrainer()

        //limit the amount of heals a trainer has in a fight
        if (trainer.healsRemaining <= 0) {
            println("${trainer.name} has no heals left!")
            return
        }

        val monster = trainer.activeMonster

        if (monster != null) {
            val maxHP = monster.stats.hp
            val currentHP = monster.stats.currenthp

            val healAmount = (maxHP * 0.3).toInt()
            val newHP = minOf(currentHP + healAmount, maxHP)

            monster.stats.currenthp = newHP
            trainer.healsRemaining--

            println("${monster.name} was healed by $healAmount HP!")
            println("${trainer.name} has ${trainer.healsRemaining} heals left.")
            endTurn()
        } else {
            println("Error: no active monster set")
        }
    }

    fun changeMonster(index: Int) {
        val trainer = getCurrentTrainer()
        if (index in 1..trainer.monsters.size) {
            val newMonster = trainer.monsters[index - 1]
            trainer.activeMonster = newMonster
            println("${trainer.name} switched to ${newMonster.name}.")
            endTurn()
        } else {
            println("Invalid monster index. Choose a number between 1 and ${trainer.monsters.size}.")
        }
    }

    fun endTurn() {
        currentTurn = (currentTurn + 1) % trainers.size // switch to the other trainer
        if (fightOver()) {
            endFight(determineWinner())
        } else {
            nextRound()
            println("Next round $rounds started! It is now ${getCurrentTrainer().name}'s turn.\n")
        }
    }

    fun nextRound(): Int {
        rounds++
        return rounds
    }

    fun fightOver(): Boolean {
        return trainers.any { trainer ->
            trainer.monsters.all { it.stats.currenthp <= 0 } // one trainer has no usable monsters left
        }
    }

    fun determineWinner(): String {
        val winnerTrainer = trainers.find { trainer ->
            trainer.monsters.any { it.stats.currenthp > 0 }
        }
        return winnerTrainer?.name ?: "No one"
    }

    fun endFight(winner: String) {
        fightStatus = false
        this.winner = winner
        println("Fight is over! The winner is: $winner")
    }

/* Will likely go into Monster.kt since it displays an attribute of the monster class
    /**
     * Displays the current monster's name and available attacks with indices.
     * Can be called from the CLI before taking an action.
     */
    fun showAvailableAttacks() {
        val monster = getCurrentMonster()
        if (monster != null) {
            println("\nCurrent Monster: ${monster.name}")
            println("Available attacks:")
            monster.attacks.forEachIndexed { i, atk ->
                println("${i + 1}) ${atk.attackSpecificData.name} (${atk.attackSpecificData.power} Power)")
            }
        } else {
            println("No active monster found.")
        }
    }
*/

}