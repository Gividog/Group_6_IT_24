package hwr.oop.monsterleague

import hwr.oop.monsterleague.TestData
import hwr.oop.monsterleague.TestData.attackZeroAccuracy
import hwr.oop.monsterleague.TestData.baseStatsHigherInitiative
import hwr.oop.monsterleague.TestData.baseStatsLowerInitiative
import hwr.oop.monsterleague.TestData.battleStatsHigherInitiative
import hwr.oop.monsterleague.TestData.battleStatsWithStatus
import hwr.oop.monsterleague.TestData.defeatedMonster
import hwr.oop.monsterleague.TestData.fireMonster
import hwr.oop.monsterleague.TestData.ghostMonster
import hwr.oop.monsterleague.TestData.physicalAttackTackle
import hwr.oop.monsterleague.TestData.specialAttackHydroPump
import hwr.oop.monsterleague.TestData.trainerWithGhostMonsterLeft
import hwr.oop.monsterleague.TestData.waterMonster
import hwr.oop.monsterleague.gamelogic.*
import hwr.oop.monsterleague.gamelogic.attacks.Attack
import hwr.oop.monsterleague.gamelogic.attacks.AttackKinds
import hwr.oop.monsterleague.gamelogic.attacks.DamagingAttack
import hwr.oop.monsterleague.gamelogic.trainers.Trainer
import hwr.oop.monsterleague.gamelogic.trainers.TrainerChoice
import hwr.oop.monsterleague.gamelogic.trainers.TrainerInBattle

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.attacks.DragonDance
import monsterleague.gamelogic.attacks.Screech
import org.junit.jupiter.api.Assertions.fail

class BattleTest : AnnotationSpec() {
  /**
   * surrender () tests
   */

  @Test
  fun `surrendering trainer causes trainer two to win the battle`() {

    val battle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithGhostMonsterLeft,
      true

    )

    battle.submitChoice(
      battle.getTrainerOne(),
      TrainerChoice.SurrenderChoice(battle.getTrainerOne()),

      )
    battle.submitChoice(
      battle.getTrainerTwo(),
      TrainerChoice.HealChoice(battle.getTrainerTwo().getActiveMonster())
    )

    assertThat(battle.getWinner()).isEqualTo(battle.getTrainerTwo())
  }

  @Test
  fun `surrendering trainer causes trainer one to win the battle`() {
    val battle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithGhostMonsterLeft,
      true

    )

    battle.submitChoice(
      battle.getTrainerOne(),
      TrainerChoice.HealChoice(battle.getTrainerOne().getActiveMonster()),

      )
    battle.submitChoice(
      battle.getTrainerTwo(),
      TrainerChoice.SurrenderChoice(battle.getTrainerOne())
    )

    assertThat(battle.getWinner()).isEqualTo(battle.getTrainerOne())
  }

  @Test
  fun `player chose same trainers, exception gets thrown`() {
    try {
      val battle = Battle(
        TestData.battleUuid,
        TestData.trainerWithTwoMonsters,
        TestData.trainerWithTwoMonsters,
        true

      )

      battle.submitChoice(
        battle.getTrainerOne(),
        TrainerChoice.SurrenderChoice(battle.getTrainerOne()),

        )
      battle.submitChoice(
        battle.getTrainerTwo(),
        TrainerChoice.HealChoice(battle.getTrainerTwo().getActiveMonster())
      )
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  /**
   * Heals Test
   */

  @Test
  fun `no heals remaining, exception gets thrown`() {
    try {
      val battle = Battle(
        TestData.battleUuid,
        TestData.trainerWithNoHealsRemaining,
        TestData.trainerWithTwoMonsters,
        true
      )
      val trainerOne = battle.getTrainerOne()
      val trainerTwo = battle.getTrainerTwo()
      val attack = TestData.physicalAttackTackle

      val trainerOneChoice =
        TrainerChoice.HealChoice(battle.getTrainerOne().getActiveMonster())
      val trainerTwoChoice = TrainerChoice.AttackChoice(
        trainerTwo.getActiveMonster(),
        attack,
        trainerOne.getActiveMonster()
      )

      battle.submitChoice(
        trainerOne,
        trainerOneChoice
      )
      battle.submitChoice(
        trainerTwo,
        trainerTwoChoice,
      )
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Test
  fun `monster to be healed, is not active Monster`() {
    try {
      val battle = Battle(
        TestData.battleUuid,
        TestData.trainerWithGhostMonsterLeft,
        TestData.trainerWithTwoMonsters,
        true
      )
      val trainerOne = battle.getTrainerOne()
      val trainerTwo = battle.getTrainerTwo()
      val attack = TestData.physicalAttackTackle
      val monsterToBeHealed = TestData.waterMonster

      val trainerOneChoice =
        TrainerChoice.HealChoice(monsterToBeHealed)
      val trainerTwoChoice = TrainerChoice.AttackChoice(
        trainerTwo.getActiveMonster(),
        attack,
        trainerOne.getActiveMonster()
      )

      battle.submitChoice(
        trainerOne,
        trainerOneChoice

      )
      battle.submitChoice(
        trainerTwo,
        trainerTwoChoice,
      )
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Test
  fun `no exception gets thrown, heals remaining gets decremented`() {

    val battle = Battle(
      TestData.battleUuid,
      TestData.trainerHealsDecrement,
      TestData.trainerWithGhostMonsterLeft,
      true

    )

    battle.submitChoice(
      battle.getTrainerOne(),
      TrainerChoice.HealChoice(battle.getTrainerOne().getActiveMonster())

    )
    battle.submitChoice(
      battle.getTrainerTwo(),
      TrainerChoice.SurrenderChoice(battle.getTrainerOne())
    )

    assertThat(battle.getTrainerOne().getHealsRemaining()).isEqualTo(2)
  }

  /**
   * AttackChoice
   */

  @Test
  fun `chosen Attack is not available`() {
    try {
      val battle = Battle(
        battleID = TestData.battleUuid,
        trainerOne = TestData.trainerWithTwoMonsters,
        trainerTwo = TestData.trainerWithGhostMonsterLeft,
        true,
      )
      val trainerOne = battle.getTrainerOne()
      val trainerTwo = battle.getTrainerTwo()

      val chosenAttack = TestData.notExistingAttack

      val attackChoiceTrainerOne = TrainerChoice.AttackChoice(
        attackingMonster = trainerOne.getActiveMonster(),
        selectedAttack = chosenAttack,
        targetedMonster = TestData.trainerWithOneDefeatedMonster.getActiveMonster()
      )

      val choiceTrainerTwo =
        TrainerChoice.HealChoice(trainerTwo.getActiveMonster())
      battle.submitChoice(
        trainerOne,
        attackChoiceTrainerOne

      )
      battle.submitChoice(
        trainerTwo,
        choiceTrainerTwo
      )
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Test
  fun `monster for chooseAttack is not active`() {
    try {
      val battle = Battle(
        battleID = TestData.battleUuid,
        trainerOne = TestData.trainerWithGhostMonsterLeft,
        trainerTwo = TestData.trainerWithTwoMonsters,
        true,
      )
      val trainerOne = battle.getTrainerOne()
      val trainerTwo = battle.getTrainerTwo()

      val attack = TestData.physicalAttackTackle
      val choiceTrainerOne =
        TrainerChoice.AttackChoice(
          TestData.fireMonster,
          attack,
          trainerTwo.getActiveMonster()
        )
      val choiceTrainerTwo =
        TrainerChoice.SurrenderChoice(trainerTwo)

      battle.submitChoice(
        trainerOne,
        choiceTrainerOne
      )
      battle.submitChoice(
        trainerTwo,
        choiceTrainerTwo
      )
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Test
  fun `attack has no powerPoints, throw exception`() {
    try {
      val battle = Battle(
        battleID = TestData.battleUuid,
        trainerOne = TestData.trainerWithNoPowerPointsAttack,
        trainerTwo = TestData.trainerWithTwoMonsters,
        true
      )
      val trainerOne = battle.getTrainerOne()
      val trainerTwo = battle.getTrainerTwo()

      val attack = TestData.attackWithoutPowerPoints
      val choiceTrainerOne =
        TrainerChoice.AttackChoice(
          trainerOne.getActiveMonster(),
          attack,
          trainerTwo.getActiveMonster()
        )
      val choiceTrainerTwo =
        TrainerChoice.HealChoice(trainerTwo.getActiveMonster())

      battle.submitChoice(
        trainerOne,
        choiceTrainerOne
      )
      battle.submitChoice(
        trainerTwo,
        choiceTrainerTwo
      )
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Test
  fun `targeted monster for chooseAttack is not active`() {
    try {
      val battle = Battle(
        battleID = TestData.battleUuid,
        trainerOne = TestData.trainerWithTwoMonsters,
        trainerTwo = TestData.trainerWithOneDefeatedMonster,
        true,
      )

      val trainerOne = battle.getTrainerOne()
      val trainerTwo = battle.getTrainerTwo()

      val attackChoice = TrainerChoice.AttackChoice(
        attackingMonster = trainerOne.getActiveMonster(),
        selectedAttack = TestData.physicalAttackTackle,
        targetedMonster = TestData.ghostMonster

      )

      val attackChoiceTwo = TrainerChoice.SurrenderChoice(trainerTwo)

      battle.submitChoice(
        trainerOne,
        attackChoice,
      )
      battle.submitChoice(
        trainerTwo,
        attackChoiceTwo
      )
      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  /**
   * Handle Attack for simple Calculation
   */

  @Test
  fun `damage is 226 `() {
    val baseStats = BaseStats(
      healthPoints = 250,
      initiative = 20,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
    )

    val battleStats = BattleStats(
      healthPoints = 250,
      initiative = 20,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
      statusEffect = null
    )
    val fireMonster = Monster(
      name = "Monster2",
      type = Type.FIRE,
      baseStats = baseStats,
      battleStats = battleStats,
      attacks = listOf(physicalAttackTackle, specialAttackHydroPump)
    )

    val trainerWithGhostMonsterLeft =
      TrainerInBattle(
        "trainer4",
        listOf(ghostMonster, defeatedMonster),
        ghostMonster,
        3
      )

    val ghostMonster = Monster(
      name = "Monster3",
      type = Type.GHOST,
      baseStats = baseStats,
      battleStats = battleStats,
      attacks = listOf(physicalAttackTackle)
    )


    val trainerWithTwoMonsters =
      TrainerInBattle(
        "trainer1",
        listOf(fireMonster),
        fireMonster,
        3
      )

    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = trainerWithGhostMonsterLeft,
      trainerTwo = trainerWithTwoMonsters,
      simpleDamageCalculation = true
    )
    val trainerOneChoice = TrainerChoice.AttackChoice(
      battle.getTrainerOne().getActiveMonster(),
      TestData.physicalAttackTackle,
      battle.getTrainerTwo().getActiveMonster()
    )

    val trainerTwoChoice = TrainerChoice.SurrenderChoice(battle.getTrainerTwo())
    battle.submitChoice(battle.getTrainerOne(), trainerOneChoice)
    battle.submitChoice(battle.getTrainerTwo(), trainerTwoChoice)

    assertThat(battle.getTrainerTwo().getActiveMonster().getHP()).isEqualTo(226)
  }

  @Test
  fun `damage calculator is not simple`(){
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithGhostMonsterLeft ,
      trainerTwo = TestData.trainerWithTwoMonsters,
      simpleDamageCalculation = false
    )
    val trainerOneChoice = TrainerChoice.AttackChoice(
      battle.getTrainerOne().getActiveMonster(),
      TestData.physicalAttackTackle,
      battle.getTrainerTwo().getActiveMonster()
    )

    val trainerTwoChoice = TrainerChoice.SurrenderChoice(battle.getTrainerTwo())
    battle.submitChoice(battle.getTrainerOne(), trainerOneChoice)
    battle.submitChoice(battle.getTrainerTwo(), trainerTwoChoice)

    assertThat(battle.getTrainerTwo().getActiveMonster().getHP()).isBetween(100,230)
  }

  @Test
  fun `attack doesnt hit`(){
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithGhostMonsterLeft ,
      trainerTwo = TestData.trainerWithTwoMonsters,
      simpleDamageCalculation = false
    )
    val trainerOneChoice = TrainerChoice.AttackChoice(
      battle.getTrainerOne().getActiveMonster(),
      attackZeroAccuracy,
      battle.getTrainerTwo().getActiveMonster()
    )

    val trainerTwoChoice = TrainerChoice.SurrenderChoice(battle.getTrainerTwo())
    battle.submitChoice(battle.getTrainerOne(), trainerOneChoice)
    battle.submitChoice(battle.getTrainerTwo(), trainerTwoChoice)

    assertThat(battle.getTrainerTwo().getActiveMonster().getHP()).isEqualTo(150)
  }

  /**
   * Buff Attacks
   */

  @Test
  fun `AttackKind is Buff`(){
    val baseStats = BaseStats(
      healthPoints = 250,
      initiative = 20,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
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


    val fireMonster = Monster(
      name = "Monster2",
      type = Type.FIRE,
      baseStats = baseStats,
      battleStats = battleStatsWithoutStatus,
      attacks = listOf(DragonDance)
    )

    val trainerBuff =
      TrainerInBattle(
        "trainer4",
        listOf(fireMonster),
        fireMonster,
        3
      )

    val ghostMonster = Monster(
      name = "Monster3",
      type = Type.GHOST,
      baseStats = baseStats,
      battleStats = battleStatsWithoutStatus,
      attacks = listOf(physicalAttackTackle)
    )
     val battle = Battle(
       battleID = TestData.battleUuid,
       trainerOne =  trainerBuff,
       trainerTwo = TestData.trainerWithGhostMonsterLeft,
       true
     )
    val attackChoice = TrainerChoice.AttackChoice(
      battle.getTrainerOne().getActiveMonster(),
      DragonDance,
      battle.getTrainerTwo().getActiveMonster()
    )
    battle.submitChoice(
      trainerBuff,
      attackChoice
    )
    battle.submitChoice(
      battle.getTrainerTwo(),
      TrainerChoice.SurrenderChoice(battle.getTrainerTwo()))

    assertThat(battle.getTrainerOne().getActiveMonster().getAttackStat()).isEqualTo(130)
    assertThat(battle.getTrainerOne().getActiveMonster().getInitiativeStat()).isEqualTo(13)

  }

  @Test
  fun `AttackKind is Debuff`(){
    val baseStats = BaseStats(
      healthPoints = 250,
      initiative = 20,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
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


    val fireMonster = Monster(
      name = "Monster2",
      type = Type.FIRE,
      baseStats = baseStats,
      battleStats = battleStatsWithoutStatus,
      attacks = listOf(Screech)
    )

    val trainerOne =
      TrainerInBattle(
        "trainer4",
        listOf(fireMonster),
        fireMonster,
        3
      )

    val trainerDebuff = TrainerInBattle(
        "trainer4",
        listOf(fireMonster),
        fireMonster,
        3
    )

    val ghostMonster = Monster(
      name = "Monster3",
      type = Type.GHOST,
      baseStats = baseStats,
      battleStats = battleStatsWithoutStatus,
      attacks = listOf(physicalAttackTackle)
    )
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne =  trainerOne,
      trainerTwo = trainerDebuff,
      true
    )
    val attackChoice = TrainerChoice.AttackChoice(
      battle.getTrainerOne().getActiveMonster(),
      Screech,
      battle.getTrainerTwo().getActiveMonster()
    )
    battle.submitChoice(
      trainerOne,
      attackChoice
    )
    battle.submitChoice(
      battle.getTrainerTwo(),
      TrainerChoice.SurrenderChoice(battle.getTrainerTwo()))

    assertThat(battle.getTrainerTwo().getActiveMonster().getDefenseStat()).isEqualTo(40)

  }
}
/*
@Test
fun `startNextRound should increment round and keep active monsters if alive`() {
  val battle = Battle(
    battleID = TestData.battleUuid,
    trainerOne = TestData.trainerWithTwoMonsters,
    trainerTwo = TestData.trainerWithOneDefeatedMonster,
    true,
  )

  battle.testStartNextRound(battle)

  assertThat(battle.getRounds()).isEqualTo(2)
  assertThat(battle.getWinner()).isNull()
  assertThat(
    battle.getTrainerOne().getActiveMonster()
  ).isEqualTo(TestData.fireMonster)
  assertThat(
    battle.getTrainerTwo().getActiveMonster()
  ).isEqualTo(TestData.waterMonster)
}

/**
 * determineWinner() tests
 */

@Test
fun `determineWinner() returns null when both trainers don't have any dead monsters`() {
  val battle = Battle(
    battleID = TestData.battleUuid,
    trainerOne = TestData.trainerWithTwoMonsters,
    trainerTwo = TestData.trainerWithOneDefeatedMonster,
    true
  )

  battle.testDetermineWinner()

  assertThat(battle.getWinner()).isEqualTo(null)
  assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithTwoMonsters)
  assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithOneDefeatedMonster)
}

@Test
fun `determineWinner() is declaring the first trainer in the list as the winner if the second trainer has no alive monsters left`() { // Test an neue Funktion anpassen
  val battle = Battle(
    battleID = TestData.battleUuid,
    trainerOne = TestData.trainerWithTwoMonsters,
    trainerTwo = TestData.trainerWithOnlyDefeatedMonsters,
    true
  )

  battle.testDetermineWinner()

  assertThat(battle.getWinner()).isEqualTo(TestData.trainerWithTwoMonsters)
  assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithOnlyDefeatedMonsters)
}

@Test
fun `second Trainer Is Winner`() {
  val battle = Battle(
    battleID = TestData.battleUuid,
    trainerOne = TestData.trainerWithOnlyDefeatedMonsters,
    trainerTwo = TestData.trainerWithTwoMonsters,
    true
  )

  battle.testDetermineWinner()

  assertThat(battle.getWinner()).isEqualTo(TestData.trainerWithTwoMonsters)
  assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithOnlyDefeatedMonsters)
}

@Test
fun `simulateRound()`() {
  // TODO
}

@Test
fun `getWinner()`() {
  // TODO
}

@Test
fun `active Monsters are sorted descending`() {
  val battle = Battle(
    battleID = TestData.battleUuid,
    trainerOne = TestData.trainerWithGhostMonsterLeft,
    trainerTwo = TestData.trainerWithTwoMonsters,
    true
  )

  val descendingSortedList = battle.testSortActiveMonsterByInitiative()
  assertThat(descendingSortedList).containsExactly(
    TestData.fireMonster,
    TestData.ghostMonster
  )
}

@Test
fun `kind of Attack is Physical`() {
  val kind =
    Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithOnlyDefeatedMonsters,
      true,
    ).getKindOfAttack(TestData.physicalAttackTackle)
  assertThat(kind).isEqualTo(AttackKinds.PHYSICAL)
}

/**
 * endRound() Tests
 */

// trainerOne branch

@Test
fun `next active Monster of trainerOne is fireMonster`() {
  val battle = Battle(
    TestData.battleUuid,
    trainerOne = TestData.trainerWithOneDefeatedMonster,
    trainerTwo = TestData.trainerWithGhostMonsterLeft,
    true,
  )

  battle.testEndRound()

  assertThat(battle.getTrainerOne().getActiveMonster()).isEqualTo(
    TestData.waterMonster
  )
  assertThat(battle.getTrainerOne().getMonsters()).isNotNull()
}

@Test
fun `no monsters left to replace trainerOne's active monsters`() {
  val battle = Battle(
    TestData.battleUuid,
    TestData.trainerWithOnlyDefeatedMonsters,
    TestData.trainerWithOneDefeatedMonster,
    true,
  )

  battle.testEndRound()

  assertThat(battle.getTrainerOne().getActiveMonster()).isEqualTo(
    TestData.defeatedMonster
  )
}

// trainerTwo branch

@Test
fun `next active Monster of trainerTwo is waterMonster`() {
  val battle = Battle(
    TestData.battleUuid,
    trainerOne = TestData.trainerWithTwoMonsters,
    trainerTwo = TestData.trainerWithGhostMonsterLeft,
    true,
  )

  battle.getTrainerTwo().setActiveMonster(TestData.defeatedMonster)

  battle.testEndRound()

  assertThat(battle.getTrainerTwo().getActiveMonster()).isEqualTo(
    TestData.ghostMonster
  )
  assertThat(battle.getTrainerTwo().getMonsters()).isNotNull()
}

@Test
fun `no monsters left to replace trainerTwo's active monsters`() {
  val battle = Battle(
    TestData.battleUuid,
    TestData.trainerWithOneDefeatedMonster,
    TestData.trainerWithOnlyDefeatedMonsters,
    true,
  )

  battle.testEndRound()

  assertThat(battle.getTrainerTwo().getActiveMonster()).isEqualTo(
    TestData.defeatedMonster
  )
}

@Test
fun `no monsters were defeated during the round so sartNextRound() is called`() {
  val battle = Battle(
    TestData.battleUuid,
    TestData.trainerWithTwoMonsters,
    TestData.trainerWithTwoMonsters,
    true,
  )

  battle.testEndRound()

  assertThat(
    battle.getTrainerOne().getActiveMonster()
  ).isEqualTo(TestData.fireMonster)
  assertThat(
    battle.getTrainerTwo().getActiveMonster()
  ).isEqualTo(TestData.fireMonster)
}

/**
 * chooseAttack tests
 */

@Test
fun `chosen Attack is not available`() {
  try {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithTwoMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
      true,
    )
    val trainer = TestData.trainerWithTwoMonsters
    val attackChoice = TrainerChoice.AttackChoice(
      attackingMonster = trainer.getActiveMonster(),
      selectedAttack = TestData.physicalAttackSplash,
      targetedMonster = TestData.trainerWithOneDefeatedMonster.getActiveMonster()

    )
    battle.trainerChooseAttack(trainer, attackChoice)
    fail("Exception should be thrown")
  } catch (e: Exception) {
    e.printStackTrace()
  }
}

@Test
fun `monster for chooseAttack is not active`(){
  try {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithOnlyDefeatedMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
      true,
    )
    val attackChoice = TrainerChoice.AttackChoice(
      attackingMonster = battle.getTrainerTwo().getActiveMonster(),
      selectedAttack = TestData.physicalAttackTackle,
      targetedMonster = battle.getTrainerOne().getActiveMonster()

    )
    battle.trainerChooseAttack(battle.getTrainerOne(), attackChoice)
    fail("Exception should be thrown")
  } catch (e: Exception) {
    e.printStackTrace()
  }
}

@Test
fun `targeted monster for chooseAttack is not active`(){
  try {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithOnlyDefeatedMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
      true,
    )
    val attackChoice = TrainerChoice.AttackChoice(
      attackingMonster = battle.getTrainerOne().getActiveMonster(),
      selectedAttack = TestData.physicalAttackTackle,
      targetedMonster = battle.getTrainerTwo().getActiveMonster()

    )
    battle.trainerChooseAttack(battle.getTrainerOne(), attackChoice)
    fail("Exception should be thrown")
  } catch (e: Exception) {
    e.printStackTrace()
  }
}

@Test
fun `Damage doesnt hit`(){
val battle = Battle(
  battleID = TestData.battleUuid,
  trainerOne = TestData.trainerWithTwoMonsters,
  trainerTwo = TestData.trainerWithTwoMonsters,
  true,
)
}

/**
 * changeActiveMonster tests
 */

@Test
fun `the newly selected monster at index 1 (so the second element) gets set as the trainers activeMonster`() {
  val battle = Battle(
    TestData.battleUuid, TestData.trainerWithTwoMonsters,
    TestData.trainerWithOneDefeatedMonster,
    true
  )
  TestData.trainerWithTwoMonsters.setMonsters(
    listOf(
      TestData.waterMonster,
      TestData.fireMonster
    )
  )
  TestData.trainerWithTwoMonsters.setActiveMonster(TestData.waterMonster)
  val switchChoice = TrainerChoice.SwitchChoice(
    outMonster = TestData.waterMonster,
    inMonster = TestData.fireMonster
  )
  battle.switchActiveMonster(TestData.trainerWithTwoMonsters, switchChoice)

  assertThat(TestData.trainerWithTwoMonsters.getActiveMonster()).isEqualTo(
    TestData.fireMonster
  )
}

@Test
fun `the new selected active monster doesnt exist in Trainers monster list`() {
  val battle = Battle(
    TestData.battleUuid, TestData.trainerWithTwoMonsters,
    TestData.trainerWithOneDefeatedMonster,
    true,
  )

  val activeMonster = TestData.trainerWithTwoMonsters.getActiveMonster()
  val switchChoice = TrainerChoice.SwitchChoice(
    outMonster = activeMonster,
    inMonster = TestData.ghostMonster
  )
  battle.switchActiveMonster(TestData.trainerWithTwoMonsters, switchChoice)
  val newSetActiveMonster = TestData.trainerWithTwoMonsters.getActiveMonster()

  assertThat(newSetActiveMonster).isEqualTo(activeMonster)
}

/**
 * healActiveMonster tests
 */

@Test
fun `healActiveMonster() heals monster's current hp (50) by 30 percent of its base hp (100) and reduces healsRemaining (0)`() {

  val battle = Battle(
    TestData.battleUuid, TestData.trainerWithTwoMonsters,
    TestData.trainerWithOneDefeatedMonster,
    true,
  )

  TestData.trainerWithTwoMonsters.setHealsRemaining(1)

  val choice =
    TrainerChoice.HealChoice(TestData.trainerWithTwoMonsters.getActiveMonster())
  battle.healActiveMonster(TestData.trainerWithTwoMonsters, choice)

  assertThat(TestData.trainerWithTwoMonsters.getActiveMonstersHP()).isEqualTo(
    225
  )
  assertThat(TestData.trainerWithTwoMonsters.getHealsRemaining()).isEqualTo(0)
}

@Test
fun `healActiveMonster() does nothing when no heals remaining`() {
  val trainer = TestData.trainerWithTwoMonsters

  trainer.setHealsRemaining(0)

  val battle = Battle(
    battleID = TestData.battleUuid,
    trainerTwo = TestData.trainerWithOnlyDefeatedMonsters,
    trainerOne = TestData.trainerWithTwoMonsters,
    simpleDamageCalculation = true
  )

  val choice =
    TrainerChoice.HealChoice(TestData.trainerWithTwoMonsters.getActiveMonster())

  battle.healActiveMonster(trainer, choice)

  assertThat(TestData.trainerWithTwoMonsters.getHealsRemaining()).isEqualTo(0)
}

@Test
fun `surrender is TrainerOne`(){
val battle = Battle(TestData.battleUuid, TestData.trainerWithTwoMonsters, TestData.trainerWithOneDefeatedMonster, true)
battle.testSurrender(battle.getTrainerTwo())
val winner = battle.getWinner()

assertThat(winner).isEqualTo(battle.getTrainerOne())

}

/* @Test
 fun `simulateRound applies damage in correct initiative order`() {
   val battle = Battle(
     battleID = TestData.battleUuid,
     trainerOne = TestData.trainerWithTwoMonsters,
     trainerTwo = TestData.trainerWithOneDefeatedMonster,
     true
   )
   val startingHP1 = TestData.trainerWithTwoMonsters.getActiveMonstersHP()
   val startingHP2 = TestData.trainerWithOneDefeatedMonster.getActiveMonstersHP()

   battle.chooseAttack(TestData.trainerWithTwoMonsters, TestData.physicalAttackTackle)
   battle.chooseAttack(TestData.trainerWithOneDefeatedMonster, TestData.physicalAttackTackle)

   assertThat(TestData.trainerWithTwoMonsters.getActiveMonstersHP()).isLessThan(startingHP1)
   assertThat(TestData.trainerWithOneDefeatedMonster.getActiveMonstersHP()).isLessThan(startingHP2)

   assertThat(battle.getChosenAttackMap()).isEmpty()
 }*/

@Test
fun `status effect gets changed from none to confused `() {
  val battleStats = TestData.battleStatsWithoutStatus
  battleStats.updateStatusEffect(Status.CONFUSED)
  val nameOfStatus = battleStats.getStatusEffect()

  assertThat(nameOfStatus).isEqualTo(Status.CONFUSED)
}

@Test
fun `TrainerTwo surrenders`() {
  val battle = Battle(
    TestData.battleUuid,
    TestData.trainerWithTwoMonsters,
    TestData.trainerWithOneDefeatedMonster,
    true
  )
  battle.testSurrender(battle.getTrainerTwo())
  val winner = battle.getWinner()

  assertThat(winner).isEqualTo(battle.getTrainerOne())
}

/*
      handleAttack Tests
*/

@Test
fun `attack hits, and gets calculated with simple calculator`(){

}



*/
