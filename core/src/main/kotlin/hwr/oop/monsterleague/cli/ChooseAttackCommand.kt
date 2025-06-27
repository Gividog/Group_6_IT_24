package hwr.oop.monsterleague.cli

import hwr.oop.CliCommand
import hwr.oop.monsterleague.gamelogic.Battle
import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice

class ChooseAttackCommand(
) : CliCommand {
val battle = BattleHolder.currentBattle?: throw Exception("No active battle")

  override fun matches(list: List<String>): Boolean {
    return list.take(2) == listOf("battle", "attack")
  }

  override fun handle(list: List<String>) {
    if (list.size < 6) {
      throw Exception("Missing parameters for attack command. Usage: battle attack --trainer=NAME --attacker=MONSTER --attack=ATTACKNAME --target=MONSTER")
    }

    val trainerName = readOption(list, "--trainer=")
    val attackerName = readOption(list, "--attacker=")
    val attackName = readOption(list, "--attack=")
    val targetName = readOption(list, "--target=")

    val trainer = battle.getTrainerByName(trainerName)
    val attacker = trainer.getMonsterByName(attackerName)
    val attack = attacker.getAttackByName(attackName)
    val targetTrainer =
      if (trainer == battle.getTrainerOne()) battle.getTrainerTwo() else battle.getTrainerOne()
    val target = targetTrainer.getMonsterByName(targetName)

    val choice = TrainerChoice.AttackChoice(
      attackingMonster = attacker,
      selectedAttack = attack,
      targetedMonster = target
    )

    battle.submitChoice(trainer, choice)

    println("Attack choice from $trainerName registered: $choice")
  }

  private fun readOption(list: List<String>, prefix: String): String {
    return list.firstOrNull { it.startsWith(prefix) }
      ?.substringAfter(prefix)
      ?: throw Exception("Missing argument: $prefix")
  }
}

