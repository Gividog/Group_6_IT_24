package hwr.oop.classes

class Fight(val fightID: Int,
            var fightStatus: Boolean,
            val trainers: List<Trainer>,
            val monsters: List<Monster>,
            var rounds: Int,
            val winner: String)
{
    var currentTurn: Int = 0

    fun getCurrentTrainer(): Trainer = trainers[currentTurn]
    fun getCurrentMonster(): Monster? = getCurrentTrainer().activeMonster //was macht das?


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

    fun chooseAction(): Int{ //
        var choice: Int?

        do {
            println("Choose your action:")
            println("1) Attack")
            println("2) Heal Monster")
            println("3) ChangeMonster")
            print("Your choice: ")

            val input = readLine()
            choice = input?.toIntOrNull() //Eingabe, die erstmal als String interpretiert wird, wird in int bzw. null Wert umgewandelt

            if (choice !in 1..3) {
                println("Invalid input")
            }

        } while (choice !in 1..3)

        return choice!! // !! ensures the Integer isn't null
    }

    fun chooseAttack() {
        // choose an attack from the monsters attack pool
        // Have to make JSON parsing work first!
    }

    fun healMonster() {
        val monster = getCurrentMonster() // could return null so do a check

        if (monster != null) {
            val maxHP = monster.stats.hp //neuer Wert überschreitet max hp nicht
            val currentHP = monster.stats.currenthp

            val healAmount = (maxHP * 0.3).toInt()
            val newHP = minOf(currentHP + healAmount, maxHP) //jeweils kleinerer Wert wird gewählt

            monster.stats.currenthp = newHP

            println("${monster.name} was healed by $healAmount HP!")

            endTurn()
        } else {
            println("Error: no active monster set")
        }
    }

    fun changeMonster() {
        // choose a monster and replace it with the monster at turn
        println("Choose a monster to replace the current one with!")
        println("Currently active: ${trainers[currentTurn].activeMonster?.name}")
        println("1) ${trainers[currentTurn].monsters[0].name}")
        println("2) ${trainers[currentTurn].monsters[1].name}")
        println("3) ${trainers[currentTurn].monsters[2].name}")
        print("Your choice: ")
    }

    fun endTurn() {
        // if (Turncount == 1) {
        // Turncount = 0;
        nextRound()
        println("Next round $rounds started!")
    }

    fun nextRound(): Int {
        // TODO save fight in case the player quits
        rounds++
        return rounds
    }

    fun fightOver(): Boolean {
        // TODO
        // if winning condition met {
        // endFight()
        // };
        return true
    }

    fun endFight(winner: String) {
        fightStatus = false
        // choose winner
        // save fight stats and winner
    }
}