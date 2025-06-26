package monsterleague.gamelogic

import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice
import hwr.oop.monsterleague.gamelogic.trainers.TrainerInBattle
import monsterleague.gamelogic.attacks.Attack

object Exceptions {
  fun attackNotFound() {
    throw Exception("Attack not found")
  }

  class AttackNotFoundException(attack: Attack, monster: Monster) :
    Exception("You tried to select $attack but $monster doesn't have this attack. Available attacks are: ${monster.getAttacks()}.")

  fun trainerChoiceNotFound() {
    throw Exception("Trainer choice not found")
  }

  class TrainerChoiceNotFoundException( // brauchen wir?
    trainer: TrainerInBattle,
    choice: TrainerChoice,
  ) :
    Exception("You tried to select $choice but it's not available to choose. Available trainer choices are: ${TrainerChoice.allowedChoiceTypes.joinToString()}.")

  fun monsterNotFound() {
    throw Exception("Monster not found")
  }

  class MonsterNotFoundException(trainer: TrainerInBattle, monster: Monster) :
    Exception("You tried to select $monster but {$trainer's} list of monsters doesn't contain this monster. Available monsters are: ${trainer.getMonsters()}.")

  fun attackCannotBeUsed() {
    throw Exception("Attack cannot be used")
  }

  class AttackCannotBeUsedException(attack: Attack, monster: Monster) :
    Exception("You tried to use $attack but it cannot be used at the moment. Available attacks are: ${monster.getAttacks()}.")

  fun monsterNotActive() {
    throw Exception("Monster not active")
  }

  class MonsterNotActiveException(monster: Monster, trainer: TrainerInBattle) :
    Exception("You tried to select $monster but $monster is not active. Currently active monster: ${trainer.getActiveMonster()}.")

  fun trainerNotFound() {
    throw Exception("Trainer not found")
  }

  class TrainerNotFoundException(trainer: TrainerInBattle, battle: Battle) :
    Exception("You tried to select $trainer but $trainer is not available to choose. Available trainers are: ${battle.getTrainerOne()} and ${battle.getTrainerTwo()}.")

  fun monsterDefeated() {
    throw Exception("Monster is defeated")
  }

  class MonsterDefeatedException(monster: Monster, trainer: TrainerInBattle) :
    Exception("You tried to select $monster as your active monster but $monster is already defeated. Currently available monsters: ${trainer.getHealthyMonsters()}.")
}