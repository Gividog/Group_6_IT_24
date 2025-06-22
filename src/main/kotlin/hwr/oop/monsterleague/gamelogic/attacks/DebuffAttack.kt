package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Type
import kotlin.math.roundToInt

class DebuffAttack(
  override val name: String,
  override val kind: AttackKinds,
  override val type: Type,
  override val accuracy: Int,
  override val power: Int,
  override val powerPoints: Int,
) : Attack {

  fun useDebuff(stat: Int): Int {
    var newStat = stat
    if (stat > 0) {
      newStat = (stat - 0.3 * stat).roundToInt()
    }
    return newStat
  }
}