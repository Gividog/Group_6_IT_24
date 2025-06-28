package hwr.oop.monsterleague.cli

import hwr.oop.monsterleague.gamelogic.Monster
import hwr.oop.monsterleague.gamelogic.cli.CliCommand
import hwr.oop.monsterleague.gamelogic.factories.MonsterFactory
import hwr.oop.monsterleague.gamelogic.factories.TrainerFactory
import hwr.oop.monsterleague.gamelogic.trainers.Trainer

class CreateTrainerCommand(
  private val trainerRepository: TrainerFactory,
) : CliCommand {
  override fun matches(list: List<String>): Boolean {
    val firstTwoAreCorrect = list.take(2) == listOf("trainer", "create")
    val hasNameOption =
      list.any { it.startsWith("--name=") && !it.endsWith("name=") }
    val hasMonstersOption = list.any { it.startsWith("--monsters=") && !it.endsWith("--monsters=") }
    return firstTwoAreCorrect && hasNameOption && hasMonstersOption
  }

  override fun handle(list: List<String>) {
    val name = parseName(list)
    val monsters = parseMonsters(list)

    if (monsters.size != 3) {
      throw IllegalArgumentException("Exactly 3 monsters must be chosen to create a trainer.")
    }

    val trainer = Trainer(name, monsters)
    trainerRepository.save(trainer)
    println("Trainer created: $trainer")
  }

  private fun parseName(list: List<String>): String {
    val nameOption = list.first { it.startsWith("--name=") }
    return nameOption.substringAfter("--name=")
  }

  private fun parseMonsters(list: List<String>): List<Monster> {
    val monsterOption = list.first { it.startsWith("--monsters=") }.substringAfter("=")
    return monsterOption
      .split(",")
      .map { it.trim() }
      .map { name -> MonsterFactory.create(name) }  // Default-Level z. B. 5
  }
}