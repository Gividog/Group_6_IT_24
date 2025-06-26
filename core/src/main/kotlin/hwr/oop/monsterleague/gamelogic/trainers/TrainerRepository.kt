package hwr.oop.monsterleague.gamelogic.trainers


object TrainerRepository {
  private val trainers = mutableMapOf<String, Trainer>()

  fun save(trainer: Trainer) {
    trainers[trainer.getTrainerName()] = trainer
  }

  fun findByName(name: String): Trainer? {
    return trainers[name]
  }
}
