package hwr.oop.monsterleague.cli

class Cli(
  loadGamePort: LoadGamePort,
  saveGamePort: SaveGamePort,
) {
  val commands = listOf(
    CreateTrainerCommand(),
    CreateBattleCommand(),
    ChooseActionCommand()
  )

  fun handle(args: List<String>) {
    val command = commands.find { it.matches(args) }
    require(command != null) { "No command found for arguments: $args" }
    command.handle(args)
  }
}