package hwr.oop.monsterleague.gamelogic

import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.attacks.Attack

class TrainerInBattle(
  private val name: String,
  private var monsters: List<Monster>,
  private var activeMonster: Monster,
  private var healsRemaining: Int,
) {
  private var readyToFight = false
  private var chosenAttack: Attack? = null

  /**
   * Queries
   **/

  fun getReadyToFight(): Boolean {
    return readyToFight
  }

  fun setReadyToFight() {
    readyToFight = true
  }

  fun setNotReadyToFight() {
    readyToFight = false
  }

  fun checkActiveMonsterDefeated(): Boolean {
    return activeMonster.defeatedMonster()
  }

  fun getName(): String {
    return name
  }

  fun getMonsters(): List<Monster> {
    return monsters
  }

  fun getActiveMonster(): Monster {
    return activeMonster
  }

  fun getActiveMonstersHP(): Int {
    return activeMonster.getHP()
  }

  fun getHealsRemaining(): Int {
    return healsRemaining
  }

  /**
   * Commands
   **/

  fun setHealsRemaining(healsRemaining: Int) {
    this.healsRemaining = healsRemaining
  }

  fun setMonsters(monsters: List<Monster>) {
    this.monsters = monsters
  }

  fun setActiveMonster(monster: Monster) {
    this.activeMonster = monster
  }
}