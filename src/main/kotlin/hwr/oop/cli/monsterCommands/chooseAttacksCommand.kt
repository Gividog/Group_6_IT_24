package hwr.oop.cli.monsterCommands

import com.github.ajalt.clikt.core.CliktCommand
import hwr.oop.classes.Monster

class chooseAttacksCommand (private val monster: Monster) : CliktCommand(name = "attacks", help = "Assigns three attacks to your monster.") {
}