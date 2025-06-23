package monsterleague.gamelogic

class Trainer(
  private val name: String,
  private val monsters: List<Monster>,
) {
  fun getTrainerName(): String {
    return name
  }

  fun getListOfMonsters(): List<Monster> {
    return monsters
  }
}
