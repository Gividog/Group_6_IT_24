//Initializer um unsere Monster als Presets festzulegen

package hwr.oop.init

import hwr.oop.classes.*
import hwr.oop.data.MonsterRepository

object MonsterInitializer {
    fun preload() {
        val uwe = Monster("Uwe", Type.Water, Stats(200, 10, 60, 80, 0, 1, 1), attacks = Attack.allAttacks)
        val johannes = Monster("Johannes", Type.Fire, Stats(500, 1, 20, 100, 0, 1, 1), attacks = Attack.allAttacks)
        val olaf = Monster("Olaf", Type.Electric, Stats(150, 40, 30, 20, 0, 1, 1), attacks = Attack.allAttacks)

        MonsterRepository.add(uwe)
        MonsterRepository.add(johannes)
        MonsterRepository.add(olaf)

    }
}