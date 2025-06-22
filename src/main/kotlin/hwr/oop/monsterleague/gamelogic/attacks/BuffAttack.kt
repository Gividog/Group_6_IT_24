package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.BattleStats
import monsterleague.gamelogic.Status
import monsterleague.gamelogic.Type
import kotlin.math.roundToInt

class BuffAttack(
  override val name: String,
  override val kind: AttackKinds,
  override val type: Type,
  override val accuracy: Int,
  override val power: Int,
  override val powerPoints: Int,
) : Attack {

  fun useBuffAttack(stat: Int) : Int{
    val newStat = stat + 0.3 * stat
    return newStat.roundToInt()
  }
}

