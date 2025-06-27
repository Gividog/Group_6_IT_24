package hwr.oop.monsterleague.gamelogic.trainers

import hwr.oop.monsterleague.gamelogic.Monster

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