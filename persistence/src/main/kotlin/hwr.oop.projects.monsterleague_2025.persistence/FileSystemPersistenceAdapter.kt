package hwr.oop.projects.kingdom_2025.persistence

import hwr.oop.projects.kingdom_2025.Game
import hwr.oop.projects.kingdom_2025.GameId

class FileSystemPersistenceAdapter : SaveGamePort, LoadGamePort {
  override fun loadGame(gameId: GameId): Game {
    TODO("Not yet implemented")
  }

  override fun saveGame(game: Game): GameId {
    TODO("Not yet implemented")
  }
}