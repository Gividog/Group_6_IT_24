package hwr.oop.classes

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
class Monster(val name: String, val type: Type, val stats: Stats, val attacks: List<Attack>) {
    fun createMonster() {
        println("What's your monster's name?\n")
        val monsterName : String? = readlnOrNull()
        println("Your monster's name is: $monsterName")

        //TODO: Schleife implementieren für den Fall einer ungültigen Eingabe
        println("What's your monster's type?\n")
        println("1: FIRE\n 2: WATER\n 3: GRASS\n 4: ELECTRIC\n 5: NORMAL\n 6: GROUND\n 7: PSYCHIC\n")
        val monsterType : Type
        val input = when (readlnOrNull()) {
            1.toString() -> monsterType = Type.FIRE
            2.toString() -> monsterType = Type.WATER
            3.toString() -> monsterType = Type.GRASS
            4.toString() -> monsterType = Type.ELECTRIC
            5.toString() -> monsterType = Type.NORMAL
            6.toString() -> monsterType = Type.GROUND
            7.toString() -> monsterType = Type.PSYCHIC

            else -> monsterType = Type.NORMAL //TODO: Muss an Schleife angepasst werden
        }
        println("Your monster's type is: $monsterType\n")

        //TODO: Auf int, gültige Eingabe/Zahl überprüfen, Punkte zählen
        println("Allocate 200 points in total. Choose wisely.")
        println("\nHow many health points does your monster have?\n")
        val monsterHp = readln()
        val monsterHpInt : Int = monsterHp.toInt()
        println("\nHow much initiative does your monster have?\n")
        val monsterInitiative = readln()
        val monsterInitiativeInt : Int = monsterInitiative.toInt()
        println("\nHow many attack points does your monster have?\n")
        val monsterAttackPoints = readln()
        val monsterAttackPointsInt : Int = monsterAttackPoints.toInt()
        println("\nHow much defense does your monster have?\n")
        val monsterDefense = readln()
        val monsterDefenseInt : Int = monsterDefense.toInt()
        println("\nHow many special attack points does your monster have?\n")
        val monsterSpecialAttack = readln()
        val monsterSpecialAttackInt : Int = monsterSpecialAttack.toInt()
        println("\nHow many special defense points does your monster have?\n")
        val monsterSpecialDefense = readln()
        val monsterSpecialDefenseInt : Int = monsterSpecialDefense.toInt()

        val monsterStats : Stats = Stats(monsterHpInt, monsterHpInt, monsterInitiativeInt, monsterAttackPointsInt, monsterDefenseInt, monsterSpecialAttackInt, monsterSpecialDefenseInt)

        println("\n Choose three out of the following eleven attacks for your monster:")
        //TODO: Json auslesen + Switch Case

      //  val monster = Monster("$monsterName", monsterType, monsterStats, monsterAttacks)
    }

    // => User erstellt Monster und legt jedes Attribut fest, u.a. die Attacken
    // => Dafür wird ihm die Json ausgelesen und er darf daraus 3 Attacken pro Monster auswählen
    // => diese werden in einer separaten Mini-Liste als Attribut für dieses Monster gespeichert

}
