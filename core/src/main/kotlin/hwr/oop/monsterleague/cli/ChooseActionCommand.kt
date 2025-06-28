package hwr.oop.monsterleague.cli

import hwr.oop.monsterleague.gamelogic.Exceptions
import hwr.oop.monsterleague.gamelogic.cli.CliCommand
import hwr.oop.monsterleague.gamelogic.factories.BattleFactory
import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice
import hwr.oop.monsterleague.gamelogic.trainers.TrainerInBattle

class ChooseActionCommand(
) : CliCommand {
  private val battle =
    BattleFactory.currentBattle ?: throw Exception("No active battle")

  override fun matches(list: List<String>): Boolean {
    return list.take(2) == listOf("trainer", "action")
  }

  override fun handle(list: List<String>) {
    if (list.size < 3) {
      throw Exception("Invalid command: Expected an action type at position 3 (attack, switch, heal, surrender).")
    }

    val actionType = list[2].lowercase()
    val trainerName = parseArg(list, "--trainer=")
    val trainer = try {
      battle.getTrainerByName(trainerName)
    } catch (e: Exception) {
      throw Exceptions.TrainerInBattleNotFoundException(trainerName, battle)
    }

    val choice = when (actionType) {
      "attack" -> parseAttackChoice(list, trainer)
      "switch" -> parseSwitchChoice(list, trainer)
      "heal" -> parseHealChoice(list, trainer)
      "surrender" -> TrainerChoice.SurrenderChoice(trainer)
      else -> {
        throw Exception("Unknown trainer action: '$actionType'. Valid options are: attack, switch, heal, surrender.")
      }
    }

    battle.submitChoice(trainer, choice)
    println("${trainer.getName()}'s action has been registered: $choice")
  }

  private fun parseAttackChoice(
    list: List<String>,
    trainer: TrainerInBattle,
  ): TrainerChoice.AttackChoice {
    val attackerName = parseArg(list, "--attacker=")
    val attackName = parseArg(list, "--attack=")
    val targetName = parseArg(list, "--target=")

    val attacker = try {
      trainer.getMonsterByName(attackerName)
    } catch (e: Exception) {
      throw Exceptions.MonsterNotFoundException(trainer, attackerName)
    }

    val attack = try {
      attacker.getAttackByName(attackName)
    } catch (e: Exception) {
      throw Exceptions.AttackNotFoundException(
        attackName = attackName,
        monster = attacker
      )
    }

    val opponent = battle.getDefendingTrainer(trainer)
    val target = try {
      opponent.getMonsterByName(targetName)
    } catch (e: Exception) {
      throw Exceptions.MonsterNotFoundException(opponent, targetName)
    }

    return TrainerChoice.AttackChoice(attacker, attack, target)
  }

  private fun parseSwitchChoice(
    list: List<String>,
    trainer: TrainerInBattle,
  ): TrainerChoice.SwitchChoice {
    val outName = parseArg(list, "--out=")

    val inName = parseArg(list, "--in=")

    val outMonster = try {
      trainer.getMonsterByName(outName)
    } catch (e: Exception) {
      throw Exceptions.MonsterNotFoundException(trainer, outName)
    }

    val inMonster = try {
      trainer.getMonsterByName(inName)
    } catch (e: Exception) {
      throw Exceptions.MonsterNotFoundException(trainer, inName)
    }

    return TrainerChoice.SwitchChoice(outMonster, inMonster)
  }

  private fun parseHealChoice(
    list: List<String>,
    trainer: TrainerInBattle,
  ): TrainerChoice.HealChoice {
    val monsterName = parseArg(list, "--monster=")

    val monster = try {
      trainer.getMonsterByName(monsterName)
    } catch (e: Exception) {
      throw Exceptions.MonsterNotFoundException(trainer, monsterName)
    }

    return TrainerChoice.HealChoice(monster)
  }

  private fun parseArg(list: List<String>, prefix: String): String {
    val arg = list.firstOrNull { it.startsWith(prefix) }
      ?: throw Exceptions.MissingRequiredArgumentException(prefix)
    val value = arg.substringAfter(prefix).trim()
    if (value.isBlank()) {
      throw Exceptions.EmptyArgumentException(prefix)
    }
    return value
  }
}


