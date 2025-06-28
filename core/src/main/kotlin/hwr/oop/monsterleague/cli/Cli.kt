package hwr.oop.monsterleague.cli

import hwr.oop.monsterleague.gamelogic.factories.TrainerFactory

class Cli(

) {
  val commands = listOf(
    CreateTrainerCommand(TrainerFactory),
    CreateBattleCommand(),
    ChooseActionCommand(),
    ChooseAttackCommand()
  )

  fun handle(args: List<String>) {
    val command = commands.find { it.matches(args) }
    require(command != null) { "No command found for arguments: $args" }
    command.handle(args)
  }
}
