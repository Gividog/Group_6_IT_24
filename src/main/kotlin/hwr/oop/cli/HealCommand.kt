package hwr.oop.cli

import com.github.ajalt.clikt.core.CliktCommand
import hwr.oop.classes.Fight

/**
 * This command allows the current trainer to heal their active monster.
 * Usage: fight heal
 */
class HealCommand(private val fight: Fight) : CliktCommand(name = "heal", help = "Heals your active monster") {

    override fun run() {
        fight.healMonster()
    }
}
