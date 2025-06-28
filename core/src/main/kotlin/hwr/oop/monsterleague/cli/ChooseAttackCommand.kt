package hwr.oop.monsterleague.cli

import hwr.oop.monsterleague.gamelogic.Exceptions
import hwr.oop.monsterleague.gamelogic.cli.CliCommand
import hwr.oop.monsterleague.gamelogic.factories.BattleFactory
import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice

class ChooseAttackCommand(
) : CliCommand {
  override fun matches(list: List<String>): Boolean {
    return list.take(2) == listOf("battle", "attack")
  }

  override fun handle(list: List<String>) {
    val battle =
      BattleFactory.currentBattle ?: throw Exception("No active battle")

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
      throw Exceptions.TrainerInBattleNotFoundException(trainerName, battle)
    }
    val attacker = try {
      trainer.getMonsterByName(attackerName)
    } catch (e: Exception) {
      throw Exceptions.MonsterNotFoundException(trainer, attackerName)
    }

    val attack = try {
      attacker.getAttackByName(attackName)
    } catch (e: Exception) {
      throw Exceptions.AttackNotFoundException(attackName, attacker)
    }

    val targetTrainer = if (trainer == battle.getTrainerOne()) {
      battle.getTrainerTwo()
    } else {
      battle.getTrainerOne()
    }

    val target = try {
      targetTrainer.getMonsterByName(targetName)
    } catch (e: Exception) {
      throw Exceptions.MonsterNotFoundException(targetTrainer, targetName)
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
    val raw = list.firstOrNull { it.startsWith(prefix) }
      ?: throw Exceptions.MissingRequiredArgumentException(prefix)

    val value = raw.removePrefix(prefix).trim()
    if (value.isBlank()) {
      throw Exceptions.EmptyArgumentException(prefix)
    }
    return value
  }

}

