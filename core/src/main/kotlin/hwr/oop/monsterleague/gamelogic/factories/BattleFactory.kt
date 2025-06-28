package hwr.oop.monsterleague.gamelogic.factories

import hwr.oop.monsterleague.gamelogic.Battle

object BattleFactory {
  var currentBattle: Battle? = null

  private val battles = mutableMapOf<String, Battle>()

  fun save(battle: Battle) {
    battles[battle.getBattleID().toString()] = battle
  }
}