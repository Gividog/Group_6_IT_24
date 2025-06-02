package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Type

class DebuffAttack (
    override val name: String,
    override val kind : AttackKinds,
    override val type: Type,
    override val accuracy: Int,
    override val power: Int,
    override val powerPoints: Int,
):Attack{

}