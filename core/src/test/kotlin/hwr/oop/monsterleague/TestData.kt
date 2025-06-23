package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.TrainerInBattle
import monsterleague.gamelogic.BaseStats
import monsterleague.gamelogic.BattleStats
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Status
import monsterleague.gamelogic.Trainer
import monsterleague.gamelogic.Type
import monsterleague.gamelogic.attacks.AttackKinds
import monsterleague.gamelogic.attacks.DamagingAttack
import monsterleague.gamelogic.attacks.SpecialAttack
import java.util.UUID

object TestData {
  val battleUuid = UUID.randomUUID()

  /**
   * Dummy Attacks
   * */

  val physicalAttackTackle =
    DamagingAttack("Tackle", AttackKinds.PHYSICAL, Type.NORMAL, 100, 50, 10)

  val physicalAttackSplash =
    DamagingAttack("Splash", AttackKinds.PHYSICAL, Type.WATER, 100, 50, 10)

  val specialAttackHydroPump =
    DamagingAttack("Hydro Pump", AttackKinds.SPECIAL, Type.WATER, 100, 75, 10)

  /**
   * Dummy Stats
   * */

  var baseStatsLowerInitiative = BaseStats(
    healthPoints = 250,
    initiative = 10,
    attack = 100,
    defense = 100,
    specialAttack = 120,
    specialDefense = 120,
  )

  var baseStatsHigherInitiative = BaseStats(
    healthPoints = 250,
    initiative = 20,
    attack = 100,
    defense = 100,
    specialAttack = 120,
    specialDefense = 120,
  )

  var baseStatsHigherAttack = BaseStats(
    healthPoints = 250,
    initiative = 10,
    attack = 120,
    defense = 100,
    specialAttack = 100,
    specialDefense = 120,
  )

  var battleStatsWithStatus = BattleStats(
    healthPoints = 250,
    initiative = 10,
    attack = 100,
    defense = 100,
    statusEffect = Status.CONFUSED,
    specialAttack = 120,
    specialDefense = 120
  )

  var battleStatsWithoutStatus = BattleStats(
    healthPoints = 250,
    initiative = 10,
    attack = 100,
    defense = 100,
    statusEffect = null,
    specialAttack = 100,
    specialDefense = 100
  )

  var battleStatsHigherAttack = BattleStats(
    healthPoints = 250,
    initiative = 10,
    attack = 120,
    defense = 100,
    statusEffect = Status.CONFUSED,
    specialAttack = 100,
    specialDefense = 120
  )

  var battleStatsDefeatedMonster = BattleStats(
    healthPoints = 0,
    initiative = 10,
    attack = 100,
    defense = 100,
    statusEffect = null,
    specialAttack = 120,
    specialDefense = 120
  )

  /**
   * Dummy Monsters
   * */

  val waterMonster = Monster(
    name = "Monster1",
    type = Type.WATER,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle),
  )

  val fireMonster = Monster(
    name = "Monster2",
    type = Type.FIRE,
    baseStats = baseStatsHigherInitiative,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle)
  )

  val ghostMonster = Monster(
    name = "Monster3",
    type = Type.GHOST,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle)
  )

  val normalMonster = Monster(
    name = "Monster4",
    type = Type.NORMAL,
    baseStats = baseStatsHigherAttack,
    battleStats = battleStatsWithStatus,
    attacks = listOf(physicalAttackTackle)
  )

  val defeatedMonster = Monster(
    name = "Monster5",
    type = Type.NORMAL,
    baseStats = baseStatsLowerInitiative,
    battleStats = battleStatsDefeatedMonster,
    attacks = listOf(physicalAttackTackle)
  )

  /**
   * Dummy Trainers in Battle
   * */

  val trainerWithTwoMonsters =
    TrainerInBattle(
      "trainer1",
      listOf(waterMonster, fireMonster),
      fireMonster,
      3
    )

  val trainerWithOneDefeatedMonster =
    TrainerInBattle(
      "trainer2",
      listOf(waterMonster, defeatedMonster),
      defeatedMonster,
      3
    )

  val trainerWithFireMonsterLeft =
    TrainerInBattle(
      "trainer3",
      listOf(fireMonster, defeatedMonster),
      defeatedMonster,
      3
    )

  val trainerWithOnlyDefeatedMonsters =
    TrainerInBattle("trainer4", listOf(defeatedMonster), defeatedMonster, 0)

  /**
   * Dummy basic Trainers
   * */

  val trainerOutOfCombat = Trainer(
    name = "trainer5",
    monsters = listOf(normalMonster, waterMonster, ghostMonster)
  )

}