package monsterleague.gamelogic

import monsterleague.gamelogic.attacks.Attack

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

    readyToFight = true
    chosenAttack = attack

    return attack!!
    //ExCeptions zum Prüfen ob Eingabe stimmt
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

  /*
  fun determineActiveMonster() : Monster {
      // TODO:
  }*/

  /**
   * Messages
   **/

  fun getReadyToFight(): Boolean {
    return readyToFight
  }

  fun setNotReadyToFight() {
    readyToFight = false
  }

  fun checkActiveMonsterDead(): Boolean {
    return activeMonster.deadMonster()
  }

  fun getChosenAttack(): Attack {

    return chosenAttack!!

  }
}
