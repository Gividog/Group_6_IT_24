package hwr.oop.cli.monsterCommands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.subcommands
import hwr.oop.classes.Monster

/**
 * This is the root command for the CLI-based Pokémon-style monster creating system.
 * It holds a reference to the current Fight instance and connects subcommands like attack, heal, etc.
 * usage: monster name monster --index / monster assign type / monster allocate points / monster choose attacks --index
 */

/*class monsterCommand (private val monster: Monster) : CliktCommand(name = "monster") {
    init {
        subcommands(
            createMonsterCommand(monster),
        )
    }

    override fun run() {
        // Just show help if no subcommand is given
        echo("Use one of the subcommands: attack, heal, change")
        // currentContext.showHelp()
    }
}*/