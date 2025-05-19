package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Status
import monsterleague.gamelogic.Type

class ComboAttack(
    override val name: String,
    override val type: Type,
    override val accuracy: Int,
    override val power: Int,
    override val powerPoints: Int,
    val statusEffect : Status
) : Attack {
}