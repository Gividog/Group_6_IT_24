package hwr.oop.classes

class Trainer(
    val name: String,
    val monsters: MutableList<Monster> = mutableListOf(),
    var activeMonster: Monster? = null
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

    fun showTeam() {
        println("\n${name}'s Team (${monsters.size}/$MAX_MONSTERS):")
        monsters.forEachIndexed { i, monster ->
            val marker = if (monster == activeMonster) "->" else "  "
            println("$marker ${i + 1}: ${monster.name} (${monster.type})")
        }
    }
}
