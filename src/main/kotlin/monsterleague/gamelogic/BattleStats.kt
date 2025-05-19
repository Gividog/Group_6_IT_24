package monsterleague.gamelogic

class BattleStats (
    var hp: Int,
    private var initiative: Int,
    private var attack: Int,
    private var defense: Int,
    private var statusEffect: Status?,
    private var specialDefense : Int,
    private var specialAttack : Int,
    private var buff : Buff?,
    private var debuff : Debuff?
){
    fun getHP() : Int {
        return hp
    }
}