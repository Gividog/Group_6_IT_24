package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack

object Exceptions {
  fun attackNotFound() {
    throw Exception("Attack not found")
  }

  class AttackNotFoundException(attack: Attack, monster: Monster) :
    Exception("You tried to select $attack but $monster doesn't have this attack. Available attacks are: ${monster.getAttacks()}.")
}