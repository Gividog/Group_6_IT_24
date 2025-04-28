package hwr.oop
import hwr.oop.classes.ListOfAttacks
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Font
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField
import hwr.oop.data.MonsterRepository
import hwr.oop.init.MonsterInitializer
import  kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.io.File

fun main() {

    val file = File("src/main/kotlin/hwr/oop/data/AttackData.json")
    val json = file.readText()

    val jsonp = Json{ignoreUnknownKeys=true}
    val listOfAttacks = jsonp.decodeFromString<ListOfAttacks>(json)

    for (index in 1 until listOfAttacks.attacks.size + 1) {
        val i : String = index.toString()
        val attack = listOfAttacks.attacks[i]
        println("Attack's name:")
        println(attack?.attackSpecificData?.name)
        println("\nAttack's power:")
        println(attack?.attackSpecificData?.power)
        println("\nAccuracy points:")
        println(attack?.attackSpecificData?.accuracy)
        println("\nPower points:")
        println(attack?.attackSpecificData?.powerPoint)
        println("\nAttack's type:")
        println(attack?.type)
        println("\nAttack's category:")
        println("${attack?.category}\n")
    }
    // MonsterRepository.loadFromFile() wenn wir unsere Monster später in einer Datei speichern

    //MonsterInitializer.preload() // Nur wenn wir wirklich die vorkonfigurierten Monster nutzen wollen

    // // TODO Console erstellen (neues Terminal); CLI Interaktion dann hier

    // MonsterRepository.saveToFile() wenn wir unsere Monster später in einer Datei speichern
}
