package monsterleague.gamelogic

class BattleStats (
    var hp: Int,
    private var initiative: Int,
    private var attack: Int,
    private var defense: Int,
    private var statusEffect: Int
){
    fun getHP() : Int {
        return hp
    }
}