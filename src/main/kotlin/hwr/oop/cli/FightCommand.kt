package hwr.oop.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import hwr.oop.classes.Fight

/**
 * This is the root command for the CLI-based Pokémon-style fight system.
 * It holds a reference to the current Fight instance and connects subcommands like attack, heal, etc.
 * usage: fight attack --index / fight heal / fight change --index
 */

class FightCommand(private val fight: Fight) : CliktCommand(name = "fight") {

    init {
        subcommands(
            AttackCommand(fight),
            HealCommand(fight),
            ChangeCommand(fight)
        )
    }

    override fun run() {
        // Just show help if no subcommand is given
        echo("Use one of the subcommands: attack, heal, change")
        // currentContext.showHelp()
    }
}
