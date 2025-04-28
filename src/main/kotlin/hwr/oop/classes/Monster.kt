package hwr.oop.classes

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

@Serializable
class Monster(val name: String, val type: Type, val stats: Stats, val attacks: List<Attack>) {
    fun createMonster(name : String, type : Type, stats : Stats, attacks : List<Attack>) {
        //val monster = Monster(name, )

        val file = File("src/main/kotlin/hwr/oop/data/AttackData.json")
        val json = file.readText()

        val jsonp = Json{ignoreUnknownKeys=true}
        val listOfAttacks = jsonp.decodeFromString<ListOfAttacks>(json)

        for (index in 1 until listOfAttacks.attacks.size + 1) {
            val i: String = index.toString()
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
    }

    // => User erstellt Monster und legt jedes Attribut fest, u.a. die Attacken
    // => Dafür wird ihm die Json ausgelesen und er darf daraus 3 Attacken pro Monster auswählen
    // => diese werden in einer separaten Mini-Liste als Attribut für dieses Monster gespeichert

}
