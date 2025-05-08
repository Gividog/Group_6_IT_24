package monsterleague.gamelogic

class AttackCalculator (
    val attackingMonster: Monster,
    val defendingMonster: Monster,
    val attack: Attack,
    val battleStats: BattleStats,
) {

    fun calculateDamage() : Int {
        var damage : Int = 0
        return damage
    }
}