package hwr.oop.classes

import hwr.oop.classes.Fight

class Trainer(
    val name: String,
    val monsters: MutableList<Monster> = mutableListOf(),
    var activeMonster: Monster? = null,
    var healsRemaining: Int = 3
) {
    private val MAX_MONSTERS = 6

    fun addMonster(monster: Monster): Boolean {
        if (monsters.size >= MAX_MONSTERS) {
            println("Du kannst nicht mehr als $MAX_MONSTERS Monster haben.")
            return false
        }
        monsters.add(monster)
        if (activeMonster == null) {
            activeMonster = monster
        }
        println("Monster ${monster.name} wurde zu deinem Team hinzugefügt.")
        return true
    }

    fun switchActiveMonster(index: Int): Boolean {
        return if (index in monsters.indices) {
            activeMonster = monsters[index]
            println("Du hast jetzt ${activeMonster?.name} als aktives Monster.")
            true
        } else {
            println("Ungültige Auswahl. Es gibt kein Monster an dieser Position.")
            false
        }
    }

    /**
     * Displays the current trainer's full team and marks the active monster.
     * Useful before using the change command.
     */
    /*
    fun showTrainerMonsters() {
        val trainer = getCurrentTrainer()
        println("\n${trainer.name}'s Monsters:")
        trainer.monsters.forEachIndexed { i, monster ->
            val activeMarker = if (monster == trainer.activeMonster) " (active)" else ""
            println("${i + 1}) ${monster.name}$activeMarker - HP: ${monster.stats.currenthp}/${monster.stats.hp}")
        }
    }*/
}
