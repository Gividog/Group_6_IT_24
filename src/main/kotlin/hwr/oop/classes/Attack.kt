package hwr.oop.classes

class Attack(val name : String, val type: Type, val category: Category, val power: Int, val accuracy: Int , val powerPoint: Int) {
    companion object {
        var allAttacks: List<Attack> = listOf()

        fun add(attacks: Attack) {
            allAttacks = allAttacks + attacks
        }

        fun getAll(): List<Attack> = allAttacks.toList()
    }
}