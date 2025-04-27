package hwr.oop.cli.monsterCommands

import com.github.ajalt.clikt.core.CliktCommand
import hwr.oop.classes.Monster

class nameMonsterCommand (private val monster: Monster) : CliktCommand(name = "name", help = "Assigns a name to your monster.") {

}