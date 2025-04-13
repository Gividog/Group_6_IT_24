package hwr.oop

import hwr.oop.data.MonsterRepository
import hwr.oop.init.MonsterInitializer

fun main() {
    // MonsterRepository.loadFromFile() wenn wir unsere Monster später in einer Datei speichern

    MonsterInitializer.preload() // Nur wenn wir wirklich die vorkonfigurierten Monster nutzen wollen

    // CLI Interaktion dann hier

    //MonsterRepository.saveToFile() wenn wir unsere Monster später in einer Datei speichern
}
//Test