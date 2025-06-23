package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack

object Exceptions {
  fun attackNotFound() {
    throw Exception("Attack not found")
  }

  class AttackNotFoundException(attack: Attack, monster: Monster) :
    Exception("You tried to select $attack but $monster doesn't have this attack. Available attacks are: ${monster.getAttacks()}.")

  // TODO : Exception für nicht existente trainer choice

  // TODO : Exception für Auswahl eines Monsters, das nicht in der Liste des Trainers existiert

  // TODO : Exception für Trainer will besiegtes Monster in die aktive Position setzen

  // TODO : Exception für Trainer will Attacke mit 0 PowerPoints einsetzen
}