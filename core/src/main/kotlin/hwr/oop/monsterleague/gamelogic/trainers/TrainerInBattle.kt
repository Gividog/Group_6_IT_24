package hwr.oop.monsterleague.gamelogic.trainers

import hwr.oop.monsterleague.gamelogic.Monster
import hwr.oop.monsterleague.gamelogic.attacks.Attack

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

  fun getHealthyMonsters(): List<Monster> {
    val healthyMonsters = monsters.filter { it.getBattleStats().getHP() > 0 }
    return healthyMonsters
  }

  fun getMonsterByName(name: String): Monster {
    return monsters.firstOrNull { it.getName() == name }
      ?: throw Exception("Monster with name '$name' not found.")
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