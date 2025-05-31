package monsterleague.gamelogic

object Exceptions {
  fun attackNotFound() {
    throw Exception("Attack not found")
  }
}