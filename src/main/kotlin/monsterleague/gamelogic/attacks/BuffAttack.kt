package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.attacks.AttackKinds
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Type

class BuffAttack (
    override val name: String,
    override val kind : AttackKinds,
    override val type: Type,
    override val accuracy: Int,
    override val power: Int,
    override val powerPoints: Int,
   ) : Attack {



    fun getBuffType(): AttackKinds {
        return kind
    }

}
    // beinhaltet sowohl Debuff-Attacken als auch Buff-Attacken (?)
