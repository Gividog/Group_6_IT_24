package hwr.oop

import hwr.oop.projects.kingdom_2025.Game
import hwr.oop.projects.kingdom_2025.Player
import hwr.oop.projects.kingdom_2025.persistence.SaveGamePort

class CreateGameCommand(
  private val saveGamePort: SaveGamePort,
) : CliCommand {

  override fun matches(list: List<String>): Boolean {
    val firstTwoAreCorrect = list.subList(0, 2) == listOf("game", "new")
    val hasPlayerOption =
      list.any { it.startsWith("--players=") && !it.endsWith("players=") }
    return firstTwoAreCorrect && hasPlayerOption
  }

  override fun handle(list: List<String>) {
    val playerStrings = parsePlayers(list)
    val players = playerStrings.map { Player(it) }
    val game = Game(players)
    val createdGameId = saveGamePort.saveGame(game)
    println("Game created with ID: $createdGameId")  // TODO Add test for this output!
  }

  private fun parsePlayers(list: List<String>): List<String> {
    val playersOption = list.first { it.startsWith("--players=") }
    val playersString = playersOption.substringAfter("--players=")
    return playersString.split(",")
  }

}