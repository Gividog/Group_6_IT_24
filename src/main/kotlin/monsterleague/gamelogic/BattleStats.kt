package monsterleague.gamelogic

class BattleStats (
    private var healthPoints: Int,
    private var initiative: Int,
    private var attack: Int,
    private var defense: Int,
    private var statusEffect: Status?,
    private var specialDefense : Int,
    private var specialAttack : Int,
){



    /**
     * Messages
     **/

    fun getHP() : Int {
        return healthPoints
    }

    fun setHP(newHP : Int) {
        healthPoints = newHP
    }

    fun updateHP(newHP: Int) {
        healthPoints = newHP
    }
}