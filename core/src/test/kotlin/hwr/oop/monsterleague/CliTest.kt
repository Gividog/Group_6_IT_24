package hwr.oop

import hwr.oop.projects.kingdom_2025.Game
import hwr.oop.projects.kingdom_2025.GameId
import hwr.oop.projects.kingdom_2025.persistence.LoadGamePort
import hwr.oop.projects.kingdom_2025.persistence.SaveGamePort
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.extensions.system.captureStandardOut
import org.assertj.core.api.Assertions.assertThat
import kotlin.collections.get

class CliTest : AnnotationSpec() {

  @Test
  fun `game new with players, persists new Game`() {
    // given
    val stub = PersistenceStub()
    val cli = Cli(
      loadGamePort = stub, saveGamePort = stub
    )
    // when
    val output = captureStandardOut {
      val args = listOf("game", "new", "--players=Alice")
      cli.handle(args)
    }.trim()
    // then
    val savedGame = stub.savedGame()
    assertThat(output).contains("Game created with ID", "1234")
    assertThat(savedGame)
      .isNotNull
      .matches { it!!.players.size == 1 }
      .matches { it!!.players[0].name == "Alice" }
  }

}

private class PersistenceStub : SaveGamePort, LoadGamePort {
  private var game: Game? = null
  private val defaultGameId = GameId("1234")

  fun savedGame(): Game? = game

  override fun saveGame(game: Game): GameId {
    this.game = game
    return defaultGameId
  }

  override fun loadGame(gameId: GameId): Game {
    if (gameId == defaultGameId) {
      return game ?: throw IllegalStateException("No game persisted yet")
    } else {
      throw IllegalStateException("Game with ID $gameId cannot be persisted")
    }
  }

}