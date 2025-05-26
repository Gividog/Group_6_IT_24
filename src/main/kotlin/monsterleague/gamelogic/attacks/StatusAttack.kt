package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.AttackKinds
import monsterleague.gamelogic.Status
import monsterleague.gamelogic.Type

class StatusAttack (
    override val name: String,
    override val kind : AttackKinds,
    override val type: Type,
    override val accuracy: Int,
    override val power: Int,
    override val powerPoints: Int,
    val statusEffect : Status
) : Attack {

}