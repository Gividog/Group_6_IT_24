package hwr.oop.monsterleague.gamelogic

import hwr.oop.monsterleague.gamelogic.calculators.DamageCalculator
import hwr.oop.monsterleague.gamelogic.calculators.HitChanceCalculator
import monsterleague.gamelogic.Battle
import monsterleague.gamelogic.Exceptions
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.AttackKinds

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

  fun setNotReadyToFight() {
    readyToFight = false
  }

  fun checkActiveMonsterDefeated(): Boolean {
    return activeMonster.defeatedMonster()
  }


  fun getChosenAttack(): Attack {
    return chosenAttack!!

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