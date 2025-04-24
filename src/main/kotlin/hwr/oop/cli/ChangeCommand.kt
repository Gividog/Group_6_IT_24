package hwr.oop.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.required
import hwr.oop.classes.Fight

/**
 * This command allows the current trainer to switch to another monster by its index.
 * Usage: fight change --index 2
 */
class ChangeCommand(private val fight: Fight) : CliktCommand(name = "change", help = "Change your active monster") {

    // Define the --index option for choosing the monster index (1-based)
    private val index by option("index", help = "Index of the monster to switch to")
        .convert { it.toInt() }
        .required()

    override fun run() {
        fight.changeMonster(index)
    }
}