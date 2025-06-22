package hwr.oop.monsterleague.gamelogic.calculators

import hwr.oop.monsterleague.gamelogic.Randomizer
import monsterleague.gamelogic.attacks.Attack

class HitChanceCalculator(
  private val attack : Attack
) {
  fun willHit(
    random: Int = Randomizer().getHitChance(),
  ): Boolean {
    val accuracy = attack.accuracy
    return random < accuracy
  }
}