package hwr.oop.data

import hwr.oop.classes.Attack

object AttackRepository {
    private val attacks = mutableListOf<Attack>()

    fun add(attack: Attack) {
        attacks.add(attack)
    }

    fun getAll(): List<Attack> = attacks.toList()

    // fun saveToFile

    // fun loadFromFile
}