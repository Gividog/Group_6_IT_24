package hwr.oop.monsterleague.gamelogic

import hwr.oop.monsterleague.gamelogic.factories.TrainerFactory
import hwr.oop.monsterleague.gamelogic.trainers.TrainerInBattle


object Exceptions {
  class AttackNotFoundException(
    attackName : String,
    monster : Monster,
  ) :
    Exception("You tried to select $attackName but $monster list of attacks doesn't contain this attack. Available attacks are: ${monster.getAttacks()}.")

  class MonsterNotFoundException(
    trainer: TrainerInBattle,
    monsterName: String,
  ) :
    Exception("You tried to select $monsterName but $trainer list of monsters doesn't contain this monster. Available monsters are: ${trainer.getMonsters()}.")

  class MonsterNotActiveException(
    monster: Monster,
    trainer: TrainerInBattle,
  ) :
    Exception("You tried to select $monster but $monster is not active. Currently active monster: ${trainer.getActiveMonster()}.")


  class TrainerNotFoundException(
    trainerName : String,
  ) :
    Exception("You tried to select $trainerName but $trainerName is not available to choose. Available trainers are: ${TrainerFactory.getAll()}.")

  class TrainerInBattleNotFoundException(
    trainerName : String,
    battle : Battle,
  ) :
    Exception("You tried to select $trainerName but $trainerName is not available to choose. Available trainers are: ${battle.getTrainerOne()} and ${battle.getTrainerTwo()}.")

  class MissingRequiredArgumentException(
    prefix : String,
  ) :
    Exception("Missing required argument: $prefix<value>")

  class EmptyArgumentException(
    prefix : String,
  ) :
    Exception("Argument $prefix must not be empty.")

  class InvalidArgumentFormatException(message: String) : Exception(message)
}