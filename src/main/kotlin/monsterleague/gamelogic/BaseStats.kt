package monsterleague.gamelogic

class BaseStats (
    private var hp: Int,
    private var initiative: Int,
    private var attack: Int,
    private var defense: Int,
    private var buff: Buff,
    private var debuff: Debuff,
    private var statusEffect: Int
) {
    fun getHP() : Int {
        return hp
    }
}