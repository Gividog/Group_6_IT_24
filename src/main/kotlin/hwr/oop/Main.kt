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

    val attackOne = listOfAttacks.attacks["1"]
    println(attackOne?.attackSpecificData?.name)
    println(attackOne?.attackSpecificData?.power)
    println(attackOne?.attackSpecificData?.accuracy)
    println(attackOne?.attackSpecificData?.powerPoint)
    println(attackOne?.type)
    println(attackOne?.category)
    // MonsterRepository.loadFromFile() wenn wir unsere Monster später in einer Datei speichern

    //MonsterInitializer.preload() // Nur wenn wir wirklich die vorkonfigurierten Monster nutzen wollen

    // // TODO Console erstellen (neues Terminal); CLI Interaktion dann hier

    // MonsterRepository.saveToFile() wenn wir unsere Monster später in einer Datei speichern
}
