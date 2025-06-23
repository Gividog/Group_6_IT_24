package monsterleague.gamelogic.attacks

import monsterleague.gamelogic.Status
import monsterleague.gamelogic.Type

class StatusAttack(
  override val name: String,
  override val kind: AttackKinds,
  override val type: Type,
  override val accuracy: Int,
  override val powerPoints: Int,
  override val power: Int,
  val statusEffect: Status,
) : Attack {

}