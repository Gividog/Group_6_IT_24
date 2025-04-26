package hwr.oop.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import hwr.oop.classes.Fight

/**
 * Root "show" command which includes subcommands like "attacks" and "monsters"
 * Usage: fight show attacks / fight show monsters
 */
class ShowCommand(fight: Fight) : CliktCommand(name = "show", help = "Show current trainer data") {

    init {
        subcommands(
            ShowAttacksCommand(fight),
            //ShowMonstersCommand(fight)
        )
    }

    override fun run() {
        echo("Use a subcommand: attacks | monsters")
        // currentContext.showHelp()
    }
}

/**
 * Subcommand to show available attacks of the current monster
 * Usage: fight show attacks
 */
class ShowAttacksCommand(private val fight: Fight) : CliktCommand(name = "attacks", help = "Show current monster's attacks") {
    override fun run() {
        fight.showAvailableAttacks()
    }
}

/**
 * Subcommand to show all monsters of the current trainer
 * Usage: fight show monsters
 */
/*
class ShowMonstersCommand(private val fight: Fight) : CliktCommand(name = "monsters", help = "Show current trainer's monsters") {
    override fun run() {
        fight.showTrainerMonsters()
    }*/

