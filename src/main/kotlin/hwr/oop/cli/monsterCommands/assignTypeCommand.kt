package hwr.oop.cli.monsterCommands

import com.github.ajalt.clikt.core.CliktCommand
import hwr.oop.classes.Monster

class assignTypeCommand (private val monster: Monster) : CliktCommand(name = "type", help = "Assigns a type to your monster.") {
}