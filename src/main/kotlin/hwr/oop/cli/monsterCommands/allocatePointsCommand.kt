package hwr.oop.cli.monsterCommands

import com.github.ajalt.clikt.core.CliktCommand
import hwr.oop.classes.Monster

class allocatePointsCommand (private val monster: Monster) : CliktCommand(name = "points", help = "Assigns points to each monster stat (200 in total).") {
}