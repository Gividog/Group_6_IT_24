package hwr.oop.cli.monsterCommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import hwr.oop.classes.Attack
import hwr.oop.classes.Monster
import hwr.oop.classes.Stats
import hwr.oop.classes.Type

/**
 *
 * This command allows the current trainer to name their newly created monster by typing in the name.
 * Usage: fight attack --index 1
 */

class createMonsterCommand (private val monster: Monster) : CliktCommand(name = "createMonster",
    help = "Assign a name and a monster type to your monster, choose three attacks and allocate 200 points in total to your monster's stats.") {
    // TODO : Falls Text aus "help" nicht automatisch einmal ausgegeben wird, println-Befehl implementieren

    private val name by option("name", help = "Assign a name to your monster.").required()

    private val type by option("type", help = "Assign a type to your monster.").required()

    private val hp by option("hp", help = "Allocate 200 points in total to your monster's stats.")
        .convert { it.toInt() }
        .required()
    private val currentHp by option("currentHp", help = "Allocate 200 points in total to your monster's stats.")
        .convert { it.toInt() }
        .required()
    private val initiative by option("initiative", help = "Allocate 200 points in total to your monster's stats.")
        .convert { it.toInt() }
        .required()
    private val attack by option("attack", help = "Allocate 200 points in total to your monster's stats.")
        .convert { it.toInt() }
        .required()
    private val defense by option("defense", help = "Allocate 200 points in total to your monster's stats.")
        .convert { it.toInt() }
        .required()
    private val specialAttack by option("specialAttack", help = "Allocate 200 points in total to your monster's stats.")
        .convert { it.toInt() }
        .required()
    private val specialDefense by option("specialDefense", help = "Allocate 200 points in total to your monster's stats.")
        .convert { it.toInt() }
        .required()

    private val attack1 by option("attack1", help = "Choose the first out of three attacks for your monster.")
        .required()
    private val attack2 by option("attack2", help = "Choose the second out of three attacks for your monster.")
        .required()
    private val attack3 by option("attack3", help = "Choose the third out of three attacks for your monster.")
        .required()



    override fun run() {
        val type = Type.valueOf(type)
        val stats = Stats(hp, currentHp, initiative, attack, defense, specialAttack, specialDefense)

        val attacks = listOf(attack1, attack2, attack3)
        val attacks = Type.valueOf(Attack)

        monster.createMonster(name, type, stats, attacks)
    }
}