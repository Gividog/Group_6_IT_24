package hwr.oop.monsterleague.gamelogic.factories

import hwr.oop.monsterleague.gamelogic.trainers.Trainer

object TrainerFactory {
  private val trainers = mutableMapOf<String, Trainer>()

  fun save(trainer: Trainer) {
    trainers[trainer.getTrainerName()] = trainer
  }

  fun findByName(name: String): Trainer? {
    return trainers[name]
  }

  fun clear(){
    trainers.clear()
  }

  fun getAll(): Map<String, Trainer> {
    return trainers.toMap()
  }
}
