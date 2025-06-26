package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.*

class Monster(
  private val name: String,
  private val type: Type,
  private val baseStats: BaseStats,
  private var battleStats: BattleStats,
  private val attacks: List<Attack>,
) {
  fun heal() {
    val healingPercentage = 0.3

    val maxHP = baseStats.getHealthPoints()
    val currentHP = getHP()

    val healAmount = (maxHP * healingPercentage).toInt()
    updateHP(minOf(currentHP + healAmount, maxHP))
  }

  fun takeDamage(damage: Int) {
    val newHP = getHP() - damage
    updateHP(newHP)
  }

  fun defeatedMonster(): Boolean {
    return getHP() <= 0
  }

  /**
   * Queries
   */

  fun getHP(): Int {
    return battleStats.getHP()
  }

  fun getName(): String {
    return name
  }

  fun getAttacks(): List<Attack> {
    return attacks
  }

  fun getType(): Type {
    return type
  }

  fun getInitiativeStat(): Int {
    return battleStats.getInitiative()
  }

  fun getDefenseStat(): Int {
    return battleStats.getDefense()
  }

  fun getAttackStat(): Int {
    return battleStats.getAttack()
  }

  fun getSpecialDefenseStat(): Int {
    return battleStats.getSpecialDefense()
  }

  fun getSpecialAttackStat(): Int {
    return battleStats.getSpecialAttack()
  }

  fun getBaseStats(): BaseStats {
    return baseStats
  }

  fun getBattleStats(): BattleStats {
    return battleStats
  }

  fun getAttackByName(name: String): Attack {
    return attacks.firstOrNull { it.getName() == name }
      ?: throw Exception("Attack $name not found for monster ${this.name}")
  }

  /**
   * Commands
   */

  fun updateHP(newHP: Int) {
    battleStats.updateHP(newHP)
  }
}


