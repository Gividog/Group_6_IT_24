package hwr.oop.monsterleague.cli

import hwr.oop.CliCommand
import hwr.oop.monsterleague.gamelogic.trainers.TrainerInBattle
import hwr.oop.monsterleague.gamelogic.factories.TrainerFactory
import hwr.oop.monsterleague.gamelogic.Battle

import java.util.UUID
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.component3

class CreateBattleCommand(
) : CliCommand {

  override fun matches(list: List<String>): Boolean {
    val firstTwoAreCorrect = list.subList(0, 2) == listOf("battle", "new")
    val hasTrainerOption =
      list.any { it.startsWith("--trainers=") && !it.endsWith("trainers=") }
    return firstTwoAreCorrect && hasTrainerOption
  }

  override fun handle(list: List<String>) {
    val trainerArg = list.first {it.startsWith("--trainers=") }
    val (trainerName1, trainerName2, simpleDamageCalculator) = parseTrainerArgs(trainerArg.removePrefix("--trainers="))

    if (trainerName1 == trainerName2) {throw Exception("Cannot create a Battle with the same trainer twice")}

    val trainerOne = TrainerFactory.findByName(trainerName1)
      ?: throw Exception("Cannot find trainer $trainerName1")
    val trainerTwo = TrainerFactory.findByName(trainerName2)
      ?: throw Exception("Cannot find trainer $trainerName2")

    val trainerInBattleOne = TrainerInBattle(
      name = trainerOne.getTrainerName(),
      monsters = trainerOne.getListOfMonsters(),
      activeMonster = trainerOne.getListOfMonsters().first(),
      healsRemaining = 3)
    val trainerInBattleTwo = TrainerInBattle(
      name = trainerTwo.getTrainerName(),
      monsters = trainerTwo.getListOfMonsters(),
      activeMonster = trainerTwo.getListOfMonsters().first(),
      healsRemaining = 3)

    val battle = Battle(UUID.randomUUID(),trainerInBattleOne, trainerInBattleTwo, simpleDamageCalculator)

    val createdBattleID = battle.getBattleID()
    BattleHolder.currentBattle = battle
    println("Battle created with ID: $createdBattleID")
  }

  private fun parseTrainerArgs(arg: String): Triple<String, String, Boolean> {
    val (trainerName1, trainerName2, simpleDamageCalculator) = arg.split(",").map { it.trim() }.also {
      require(it.size == 3) {
        "Expected format: --trainers=Ash,Misty,0" } // wird später Exception
    }
    return Triple(trainerName1, trainerName2, simpleDamageCalculator == "0")
  }
}