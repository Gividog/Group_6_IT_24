//Initializer um unsere Monster als Presets festzulegen

package hwr.oop.init

import hwr.oop.classes.*
import hwr.oop.data.MonsterRepository

object MonsterInitializer {
    fun preload() {
        val uwe = Monster("Uwe", Type.Wasser, Stats(200, 10, 60, 80, 0, 1))
        val johannes = Monster("Johannes", Type.Feuer, Stats(500, 1, 20, 100, 0, 1))
        val olaf = Monster("Olaf", Type.Elektro, Stats(150, 40, 30, 20, 0, 1))

        MonsterRepository.add(uwe)
        MonsterRepository.add(johannes)
        MonsterRepository.add(olaf)
    }
}