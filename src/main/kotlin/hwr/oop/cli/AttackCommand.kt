package hwr.oop.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.required
import hwr.oop.classes.Fight

/**
 * This command allows the current trainer to use an attack by specifying its index.
 * Usage: fight attack --index 1
 */
class AttackCommand(private val fight: Fight) : CliktCommand(name = "attack", help = "Perform an attack by index") {

    // Define the --index option for choosing the attack index (1-based)
    private val index by option("index", help = "Index of the attack to use")
        .convert { it.toInt() }
        .required()

    override fun run() {
        fight.chooseAttack(index)
    }
}