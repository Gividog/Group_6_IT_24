package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Buff
import monsterleague.gamelogic.Debuff
import monsterleague.gamelogic.Type

class BuffAttack (
    override val name: String,
    override val type: Type,
    override val accuracy: Int,
    override val power: Int,
    override val powerPoints: Int,
    val buff : Buff,
    val debuff : Debuff
) : Attack {
    // beinhaltet sowohl Debuff-Attacken als auch Buff-Attacken (?)
}