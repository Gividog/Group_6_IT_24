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