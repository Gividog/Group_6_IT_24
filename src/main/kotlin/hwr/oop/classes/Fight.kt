package hwr.oop.classes

class Fight(val fightID: Int,
            var fightStatus: Boolean,
            val trainers: List<Trainer>,
            val monsters: List<Monster>,
            val rounds: Int,
            val winner: String)
{
    var currentTurn: Int = 0

    fun getCurrentTrainer(): Trainer = trainers[currentTurn]
    fun getCurrentMonster(): Monster? = getCurrentTrainer().activeMonster


    fun startFight() {
        fightStatus = true;
    }

    fun playTurn() {
        when (chooseAction()) {
            1 -> chooseAttack()
            2 -> healMonster()
            3 -> changeMonster()
        }
    }

    fun chooseAction(): Int{
        var choice: Int?

        do {
            println("Choose what to do")
            println("1) Attack")
            println("2) Heal Monster")
            println("3) ChangeMonster")
            print("Your choice: ")

            val input = readLine()
            choice = input?.toIntOrNull()

            if (choice !in 1..3) {
                println("Invalid input")
            }

        } while (choice !in 1..3)

        return choice!! // !! ensures the Integer isn't null
    }

    fun chooseAttack() {
        // choose an attack from the monsters attack pool
        //
    }

    fun healMonster() {
        val monster = getCurrentMonster() // could return null so do a check

        if (monster != null) {
            val maxHP = monster.stats.hp
            val currentHP = monster.stats.currenthp

            val healAmount = (maxHP * 0.3).toInt()
            val newHP = minOf(currentHP + healAmount, maxHP)

            monster.stats.currenthp = newHP

            println("${monster.name} was healed by $healAmount HP!")

            endTurn()
        } else {
            println("Error: no active monster set")
        }
    }

    fun changeMonster() {
        // display all monsters the trainer has LEFT
        // choose a monster and replace it with the monster at turn
    }

    fun endTurn() {
        // if (Turncount == 1) {
            // Turncount = 0;
    }

    fun nextRound(): Int {
        // TODO
        // save fight in case the player wants to quit
        // rounds++
        // changeTurns
    }

    fun fightOver(): Boolean {
        // TODO
        // if winning condition met {
        // endFight()
        // };
    }

    fun endFight(winner: String) {
        fightStatus = false
        // choose winner
        // save fight stats and winner
    }
}