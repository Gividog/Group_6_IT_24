package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.Exceptions

class Trainer(
  val name: String,
  var monsters: List<Monster>,
  var activeMonster: Monster,
  var healsRemaining: Int,
) {
  private var readyToFight = false
  private var chosenAttack: Attack? = null

  fun trainerChooseAttack(selectedAttack: Attack): Attack {
    val attackingMonster = activeMonster
    val attack = attackingMonster.attacks.find { it == selectedAttack }
    if (attack == null) {
      Exceptions.attackNotFound()
    }
    readyToFight = true
    chosenAttack = attack

    return attack!!
  }

  fun switchActiveMonster(monster: Monster) {
    if (monster in monsters) activeMonster = monster
    readyToFight = true
  }

  fun healActiveMonster() {
    if (healsRemaining > 0) {
      activeMonster.heal()
      healsRemaining--

      readyToFight = true
    }
  }

  /**
   * Messages
   **/

  fun setNotReadyToFight() {
    readyToFight = false
  }

}
