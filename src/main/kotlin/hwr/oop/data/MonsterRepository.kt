// Hiermit speichern wir unsere Monster

package hwr.oop.data

import hwr.oop.classes.Monster

object MonsterRepository {
    var allMonsters: List<Monster> = listOf()

    fun add(monster: Monster) {
        allMonsters = allMonsters + monster
    }

    fun getAll(): List<Monster> = allMonsters.toList()

    // fun saveToFile

    // fun loadFromFile
}
