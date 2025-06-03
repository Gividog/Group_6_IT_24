package hwr.oop.monsterleague.gamelogic

import monsterleague.gamelogic.Exceptions
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

    fun trainerChooseAttack(selectedAttack: Attack): Attack {
        val attackingMonster = activeMonster
        val attack = attackingMonster.getAttacks().find { it == selectedAttack }
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
     * Queries
     **/

    fun getReadyToFight(): Boolean {
        return readyToFight
    }

    fun setNotReadyToFight() {
        readyToFight = false
    }

    fun checkActiveMonsterDead(): Boolean {
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