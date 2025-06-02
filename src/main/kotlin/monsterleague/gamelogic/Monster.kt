package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.*

class Monster(
  val name: String,
  val type: Type,
  val baseStats: BaseStats,
  var battleStats: BattleStats,
  val attacks: List<Attack>,
) {
  fun heal() {
    val healingPercentage = 0.3

    val maxHP = baseStats.getHealthPoints()
    val currentHP = battleStats.getHP()

    val healAmount = (maxHP * healingPercentage).toInt()
    battleStats.updateHP(minOf(currentHP + healAmount, maxHP))
  }

  fun takeDamage(damage: Int) {
    val newHP = battleStats.getHP() - damage
    battleStats.updateHP(newHP)
  }

  fun deadMonster(): Boolean { //lieber defeatedMonster
    return battleStats.getHP() <= 0
  }

  fun getHP(): Int {
    return battleStats.getHP()
  }
}

