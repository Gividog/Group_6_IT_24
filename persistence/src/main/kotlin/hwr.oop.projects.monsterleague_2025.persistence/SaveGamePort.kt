package hwr.oop.projects.kingdom_2025.persistence

import hwr.oop.projects.kingdom_2025.Game
import hwr.oop.projects.kingdom_2025.GameId

interface SaveGamePort {
  fun saveGame(game: Game): GameId

}