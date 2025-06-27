package hwr.oop.monsterleague.cli

import hwr.oop.CliCommand
import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice

class ChooseAttackCommand(
) : CliCommand {
  override fun matches(list: List<String>): Boolean {
    return list.take(2) == listOf("battle", "attack")
  }

  override fun handle(list: List<String>) {
    val battle =
      BattleHolder.currentBattle ?: throw Exception("No active battle")

    if (list.size < 6) {
      throw Exception("Missing parameters for attack command. Usage: battle attack --trainer=NAME --attacker=MONSTER --attack=ATTACKNAME --target=MONSTER")
    }

    val trainerName = readOption(list, "--trainer=")
    val attackerName = readOption(list, "--attacker=")
    val attackName = readOption(list, "--attack=")
    val targetName = readOption(list, "--target=")

    val trainer = try {
      battle.getTrainerByName(trainerName)
    } catch (e: Exception) {
      throw Exception("No trainer with name: $trainerName")
    }
    val attacker = try {
      trainer.getMonsterByName(attackerName)
    } catch (e: Exception) {
      throw Exception("No monster with name '$attackerName' for trainer '$trainerName'")
    }

    val attack = try {
      attacker.getAttackByName(attackName)
    } catch (e: Exception) {
      throw Exception("No attack with name '$attackName' on monster '$attackerName'")
    }

    val targetTrainer = if (trainer == battle.getTrainerOne()) {
      battle.getTrainerTwo()
    } else {
      battle.getTrainerOne()
    }

    val target = try {
      targetTrainer.getMonsterByName(targetName)
    } catch (e: Exception) {
      throw Exception("No monster with name '$targetName' for opposing trainer '${targetTrainer.getName()}'")
    }

    val choice = TrainerChoice.AttackChoice(
      attackingMonster = attacker,
      selectedAttack = attack,
      targetedMonster = target
    )

    battle.submitChoice(trainer, choice)

    println("Attack choice from $trainerName registered: $choice")
  }

  private fun readOption(list: List<String>, prefix: String): String {
    val value = list.firstOrNull { it.startsWith(prefix) }
      ?.substringAfter(prefix)
      ?: throw Exception("Missing argument: $prefix")

    if (value.isBlank()) {
      throw Exception("Missing argument: $prefix")
    }
    return value
  }
}

