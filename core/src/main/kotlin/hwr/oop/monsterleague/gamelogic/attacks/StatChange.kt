package hwr.oop.monsterleague.gamelogic.attacks

sealed interface StatChange {
  class Buff(
    val attackSteps: Int = 0,
    val defenseSteps: Int = 0,
    val specialAttackSteps: Int = 0,
    val specialDefenseSteps: Int = 0,
    val initiativeSteps: Int = 0,
  ) : StatChange

  data object Clear : StatChange
}