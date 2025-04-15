package hwr.oop.classes

class Monster(val name: String, val type: Type,val stats: Stats, val attacks: List<Attack>) {

    companion object {
        var allMonsters: List<Monster> = listOf()

        fun register(monster: Monster) {
            allMonsters = allMonsters + monster
        }

        fun getAll(): List<Monster> = allMonsters.toList()
    }
}
