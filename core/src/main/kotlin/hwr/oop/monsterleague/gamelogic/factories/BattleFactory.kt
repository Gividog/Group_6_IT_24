package hwr.oop.monsterleague.gamelogic.factories

import hwr.oop.monsterleague.gamelogic.Battle

object BattleFactory {
  private val battles = mutableMapOf<String, Battle>()

  fun save(battle: Battle) {
    battles[battle.getBattleID().toString()] = battle
  }

  fun getBattleByUuid(uuid: String): Battle? {
    return battles[uuid]
  }

}