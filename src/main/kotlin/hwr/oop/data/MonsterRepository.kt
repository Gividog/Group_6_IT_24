// Hiermit speichern wir unsere Monster

package hwr.oop.data

import hwr.oop.classes.Monster

object MonsterRepository {
    private val monsters = mutableListOf<Monster>()

    fun add(monster: Monster) {
        monsters.add(monster)
    }

    fun getAll(): List<Monster> = monsters.toList()

    // fun saveToFile

    // fun loadFromFile
}
