package monsterleague.gamelogic

class BattleStats(
  private var healthPoints: Int,
  private var initiative: Int,
  private var attack: Int,
  private var defense: Int,
  private var statusEffect: Status?,
  private var specialDefense: Int,
  private var specialAttack: Int,
) {

  /**
   * Get Messages
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

  /**
   * Update Messages
   **/

  fun updateHP(newHP: Int) {
    healthPoints = newHP
  }
}