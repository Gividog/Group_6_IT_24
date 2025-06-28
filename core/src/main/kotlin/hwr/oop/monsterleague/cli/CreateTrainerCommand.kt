package hwr.oop.monsterleague.cli

import hwr.oop.monsterleague.gamelogic.Exceptions
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
      list.any { it.startsWith("--name=") && it.length > "--name=".length }
    val hasMonstersOption =
      list.any { it.startsWith("--monsters=") && it.length > "--monsters=".length }
    return firstTwoAreCorrect && hasNameOption && hasMonstersOption
  }

  override fun handle(list: List<String>) {
    val name = parseName(list)
    val monsters = parseMonsters(list)

    val trainer = Trainer(name, monsters)
    trainerRepository.save(trainer)
    println("Trainer created: $trainer")
  }

  private fun parseName(list: List<String>): String {
    val nameOption = list.find { it.startsWith("--name=") }
      ?: throw Exceptions.MissingRequiredArgumentException("--name=")

    val name = nameOption.substringAfter("--name=").trim()
    if (name.isBlank()) {
      throw Exceptions.EmptyArgumentException("--name=")
    }
    return name
  }

  private fun parseMonsters(list: List<String>): List<Monster> {
    val monstersRaw = list.find { it.startsWith("--monsters=") }
      ?.substringAfter("=")
      ?.takeIf { it.isNotBlank() }
      ?: throw Exceptions.MissingRequiredArgumentException("--monsters=")

    val monsterNames = monstersRaw.split(",").map { it.trim() }
    if (monsterNames.size != 3) {
      throw IllegalArgumentException(
        "Exactly 3 monsters must be provided, but got ${monsterNames.size}."
      )
    }

    return monsterNames.map { name ->
      try {
        MonsterFactory.create(name)
      } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Unknown monster: '$name'")
      }
    }
  }
}