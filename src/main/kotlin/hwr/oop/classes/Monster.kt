package hwr.oop.classes

class Monster(val name: String, val type: Type,val stats: Stats ) {

    companion object {
        private val allMonsters = mutableListOf<Monster>()

        fun register(monster: Monster) {
            allMonsters.add(monster)
        }

        fun getAll(): List<Monster> = allMonsters.toList()
    }
}
