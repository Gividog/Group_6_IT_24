package monsterleague.gamelogic

class BaseStats (
    private var hp: Int,
    private var initiative: Int,
    private var attack: Int,
    private var defense: Int,
    private var specialDefense : Int,
    private var specialAttack : Int
) {
    fun getHP() : Int {
        return hp
    }
}