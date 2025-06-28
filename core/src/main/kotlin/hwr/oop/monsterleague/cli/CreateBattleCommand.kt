package hwr.oop.monsterleague.cli

import hwr.oop.monsterleague.gamelogic.Exceptions
import hwr.oop.monsterleague.gamelogic.trainers.TrainerInBattle
import hwr.oop.monsterleague.gamelogic.factories.TrainerFactory
import hwr.oop.monsterleague.gamelogic.Battle
import hwr.oop.monsterleague.gamelogic.cli.CliCommand
import hwr.oop.monsterleague.gamelogic.factories.BattleFactory
import java.util.UUID

class CreateBattleCommand(
) : CliCommand {

  override fun matches(list: List<String>): Boolean {
    val firstTwoAreCorrect = list.take(2) == listOf("battle", "new")
    val hasTrainerOption =
      list.any { it.startsWith("--trainers=") && !it.endsWith("trainers=") }
    return firstTwoAreCorrect && hasTrainerOption
  }

  override fun handle(list: List<String>) {
    val trainerArg = list.firstOrNull { it.startsWith("--trainers=") }
      ?: throw Exceptions.MissingRequiredArgumentException("--trainers=")

    val (trainerName1, trainerName2, simpleDamageCalculator) = parseTrainerArgs(
      trainerArg.removePrefix("--trainers=")
    )

    if (trainerName1 == trainerName2) {
      throw Exception("Cannot create a battle with the same trainer twice: '$trainerName1'")
    }

    val trainerOne = TrainerFactory.findByName(trainerName1)
      ?: throw Exceptions.TrainerNotFoundException(trainerName1)
    val trainerTwo = TrainerFactory.findByName(trainerName2)
      ?: throw Exceptions.TrainerNotFoundException(trainerName2)

    val trainerInBattleOne = TrainerInBattle(
      name = trainerOne.getTrainerName(),
      monsters = trainerOne.getListOfMonsters(),
      activeMonster = trainerOne.getListOfMonsters().first(),
      healsRemaining = 3
    )
    val trainerInBattleTwo = TrainerInBattle(
      name = trainerTwo.getTrainerName(),
      monsters = trainerTwo.getListOfMonsters(),
      activeMonster = trainerTwo.getListOfMonsters().first(),
      healsRemaining = 3
    )

    val battle = Battle(
      UUID.randomUUID(),
      trainerInBattleOne,
      trainerInBattleTwo,
      simpleDamageCalculator
    )

    BattleFactory.currentBattle = battle
    BattleFactory.save(battle)
    println("Battle created with ID: ${battle.getBattleID()}")
  }

  private fun parseTrainerArgs(arg: String): Triple<String, String, Boolean> {
    val parts = arg.split(",").map { it.trim() }
    if (parts.size != 3) {
      throw Exceptions.InvalidArgumentFormatException("Expected format: --trainers=Trainer1,Trainer2,0 or 1")
    }
    val (trainerName1, trainerName2, damageFlagStr) = parts

    val simpleDamageCalculator = when (damageFlagStr) {
      "0" -> true
      "1" -> false
      else -> throw Exceptions.InvalidArgumentFormatException("Third argument must be 0 (simple damage) or 1 (complex damage), got '$damageFlagStr'")
    }

    if (trainerName1.isBlank() || trainerName2.isBlank()) {
      throw Exceptions.EmptyArgumentException("Trainer names cannot be empty.")
    }

    return Triple(trainerName1, trainerName2, simpleDamageCalculator)
  }
}