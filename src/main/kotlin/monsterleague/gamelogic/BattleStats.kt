package monsterleague.gamelogic

class BattleStats (
    var hp: Int,
    var initiative: Int,
    private var attack: Int,
    private var defense: Int,
    private var statusEffect: Status?,
    private var specialDefense : Int,
    private var specialAttack : Int,
){
    fun getHP() : Int {
        return hp
    }
}