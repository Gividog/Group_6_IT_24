package hwr.oop.classes

class Attack(val name : String, val type: Type, val damage: Int, val accuracy: Int ) {
    companion object {
        private val allAttacks = mutableListOf<Attack>()

        fun register(attack: Attack) {
            allAttacks.add(attack)
        }

        fun getAll(): List<Attack> = allAttacks.toList()
    }
}