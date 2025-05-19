package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Type

class PhysicalAttack(
    override val name: String,
    override val type: Type,
    val accuracy: Int,
    val power: Int,
    val powerPoints: Int
) : Attack {
}