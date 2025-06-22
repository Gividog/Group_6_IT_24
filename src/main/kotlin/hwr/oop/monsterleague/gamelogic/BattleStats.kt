package monsterleague.gamelogic

import hwr.oop.monsterleague.gamelogic.attacks.StatusChange

class BattleStats(
  private var healthPoints: Int,
  private var initiative: Int,
  private var attack: Int,
  private var defense: Int,
  private var statusEffect: Status?,
  private var specialDefense: Int,
  private var specialAttack: Int,
) {

  companion object {
    fun createBasedOn(baseStats: BaseStats) = BattleStats(
      healthPoints = baseStats.getHealthPoints(),
      initiative = baseStats.getInitiative(),
      attack = baseStats.getAttack(),
      defense = baseStats.getDefense(),
      statusEffect = null,
      specialDefense = baseStats.getSpecialDefense(),
      specialAttack = baseStats.getSpecialAttack(),
    )
  }

  fun applyChange(buff: StatusChange, monster: Monster) {
    when (buff) {
      is StatusChange.Buff -> applyBuff(buff)
      is StatusChange.Clear -> resetStatsToBase(monster.getBaseStats())
    }
  }

  private fun applyBuff(buff: StatusChange.Buff) {
    attack = newStat(attack, buffedBy = buff.attackSteps)
    defense = newStat(defense, buffedBy = buff.defenseSteps)
    specialAttack = newStat(specialAttack, buffedBy = buff.specialAttackSteps)
    specialDefense = newStat(specialDefense, buffedBy = buff.specialDefenseSteps)
    initiative = newStat(initiative, buffedBy = buff.initiativeSteps)
  }

  private fun resetStatsToBase(baseStats: BaseStats) {
    attack = baseStats.getAttack()
    defense = baseStats.getDefense()
    statusEffect = null
    specialAttack = baseStats.getSpecialAttack()
    specialDefense = baseStats.getSpecialDefense()
    initiative = baseStats.getInitiative()
  }

  private fun newStat(stat: Int, buffedBy: Int): Int {
    return stat + (stat * 0.3 * buffedBy).toInt()
  }

  /**
   * Queries
   **/

  fun getHP(): Int {
    return healthPoints
  }

  fun getSpecialDefense(): Int {
    return specialDefense
  }

  fun getSpecialAttack(): Int {
    return specialAttack
  }

  fun getDefense(): Int {
    return defense
  }

  fun getAttack(): Int {
    return attack
  }

  fun getInitiative():Int{
    return initiative
  }

  fun getStatusEffect(): Status?{
    return statusEffect
  }

  /**
   * Commands
   **/

  fun updateHP(newHP: Int) {
    healthPoints = newHP
  }

  fun updateStatusEffect(newStatusEffect: Status ){
    statusEffect = newStatusEffect
  }
}