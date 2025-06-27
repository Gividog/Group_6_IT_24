package hwr.oop.monsterleague.gamelogic.attacks

import hwr.oop.monsterleague.gamelogic.Status
import hwr.oop.monsterleague.gamelogic.Type
import hwr.oop.monsterleague.gamelogic.attacks.Attack
import hwr.oop.monsterleague.gamelogic.attacks.AttackKinds

class StatusAttack(
  override val name: String,
  override val kind: AttackKinds,
  override val type: Type,
  override val power: Int,
  override val accuracy: Int,
  override val powerPoints: Int,
  val statusEffect: Status,
) : Attack {

}