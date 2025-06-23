package hwr.oop

import hwr.oop.projects.kingdom_2025.persistence.LoadGamePort
import hwr.oop.projects.kingdom_2025.persistence.SaveGamePort

class Cli(
  loadGamePort: LoadGamePort,
  saveGamePort: SaveGamePort,
) {
  val commands = listOf(
    CreateGameCommand(saveGamePort),
    LoadGameCommand(loadGamePort)
  )

  fun handle(args: List<String>) {
    val command = commands.find { it.matches(args) }
    require(command != null) { "No command found for arguments: $args" }
    command.handle(args)
  }
}