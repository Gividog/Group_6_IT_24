package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Type

class PhysicalAttack(
    val name: String,
    val type: Type,
    val accuracy: Int,
    val power: Int,
    val powerPoints: Int
) {
}