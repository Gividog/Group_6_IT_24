package hwr.oop.monsterleague

import hwr.oop.monsterleague.TestData.attackZeroAccuracy
import hwr.oop.monsterleague.TestData.defeatedMonster
import hwr.oop.monsterleague.TestData.fireMonster
import hwr.oop.monsterleague.TestData.ghostMonster
import hwr.oop.monsterleague.TestData.normalMonster
import hwr.oop.monsterleague.TestData.physicalAttackTackle
import hwr.oop.monsterleague.TestData.specialAttackHydroPump
import hwr.oop.monsterleague.TestData.waterMonster
import hwr.oop.monsterleague.gamelogic.*
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
  fun `damage calculator is not simple`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithGhostMonsterLeft,
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

    assertThat(battle.getTrainerTwo().getActiveMonster().getHP()).isBetween(
      100,
      230
    )
  }

  @Test
  fun `attack doesnt hit`() {
    val trainerOne = TrainerInBattle(
      "trainer1",
      listOf(ghostMonster, defeatedMonster),
      ghostMonster,
      3
    )

    val trainerTwo = TrainerInBattle(
      "trainer2",
      listOf(waterMonster, fireMonster),
      waterMonster,
      3
    )
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = trainerOne,
      trainerTwo = trainerTwo,
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

    assertThat(battle.getTrainerTwo().getActiveMonster().getHP()).isEqualTo(250)
  }

  @Test
  fun `player chose special attack`() {
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
    val ghostMonster = Monster(
      name = "Monster3",
      type = Type.GHOST,
      baseStats = baseStats,
      battleStats = battleStats,
      attacks = listOf(physicalAttackTackle, specialAttackHydroPump)
    )

    val trainerWithGhostMonsterLeft =
      TrainerInBattle(
        "trainer4",
        listOf(ghostMonster, fireMonster),
        ghostMonster,
        3
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
      ghostMonster,
      TestData.specialAttackHydroPump,
      fireMonster,
    )

    val trainerTwoChoice = TrainerChoice.SurrenderChoice(battle.getTrainerTwo())
    battle.submitChoice(battle.getTrainerOne(), trainerOneChoice)
    battle.submitChoice(battle.getTrainerTwo(), trainerTwoChoice)

    assertThat(battle.getTrainerTwo().getActiveMonster().getHP()).isEqualTo(215)
  }

  /**
   * Buff Attacks
   */

  @Test
  fun `AttackKind is Buff`() {
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
      trainerOne = trainerBuff,
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
      TrainerChoice.SurrenderChoice(battle.getTrainerTwo())
    )

    assertThat(
      battle.getTrainerOne().getActiveMonster().getAttackStat()
    ).isEqualTo(130)
    assertThat(
      battle.getTrainerOne().getActiveMonster().getInitiativeStat()
    ).isEqualTo(13)

  }

  @Test
  fun `AttackKind is Debuff`() {
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
      trainerOne = trainerOne,
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
      TrainerChoice.SurrenderChoice(battle.getTrainerTwo())
    )

    assertThat(
      battle.getTrainerTwo().getActiveMonster().getDefenseStat()
    ).isEqualTo(40)

  }

  /**
   * Switch Choice
   */

  @Test
  fun `trainer tries to switch active Monster with defeated monster, Exception gets thrown`() {
    try {
      val trainer = TrainerInBattle(
        "TrainerOne",
        listOf(fireMonster, waterMonster),
        fireMonster,
        3
      )

      val trainerTwo = TrainerInBattle(
        "TrainerOne",
        listOf(defeatedMonster, fireMonster),
        fireMonster,
        3
      )
      val battle = Battle(
        battleID = TestData.battleUuid,
        trainerOne = trainer,
        trainerTwo = trainerTwo,
        true
      )

      val choiceTrainerOne = TrainerChoice.SurrenderChoice(trainer)
      val choiceTrainerTwo =
        TrainerChoice.SwitchChoice(fireMonster, defeatedMonster)

      battle.submitChoice(trainer, choiceTrainerOne)
      battle.submitChoice(trainerTwo, choiceTrainerTwo)

      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Test
  fun `trainer tries to switch Monster with not existing Monster`() {
    try {
      val trainer = TrainerInBattle(
        "TrainerOne",
        listOf(fireMonster, waterMonster),
        fireMonster,
        3
      )

      val trainerTwo = TrainerInBattle(
        "TrainerOne",
        listOf(defeatedMonster, fireMonster),
        fireMonster,
        3
      )
      val battle = Battle(
        battleID = TestData.battleUuid,
        trainerOne = trainer,
        trainerTwo = trainerTwo,
        true
      )

      val choiceTrainerOne =
        TrainerChoice.SwitchChoice(fireMonster, normalMonster)
      val choiceTrainerTwo = TrainerChoice.SurrenderChoice(trainerTwo)


      battle.submitChoice(trainer, choiceTrainerOne)
      battle.submitChoice(trainerTwo, choiceTrainerTwo)

      fail("Exception should be thrown")
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  @Test
  fun `trainer switches Monster fire to water Monster`() {
    val trainer = TrainerInBattle(
      "TrainerOne",
      listOf(fireMonster, waterMonster),
      fireMonster,
      3
    )

    val trainerTwo = TrainerInBattle(
      "TrainerOne",
      listOf(defeatedMonster, fireMonster),
      fireMonster,
      3
    )
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = trainer,
      trainerTwo = trainerTwo,
      true
    )

    val choiceTrainerOne = TrainerChoice.SwitchChoice(fireMonster, waterMonster)
    val choiceTrainerTwo = TrainerChoice.SurrenderChoice(trainerTwo)


    battle.submitChoice(trainer, choiceTrainerOne)
    battle.submitChoice(trainerTwo, choiceTrainerTwo)

    assertThat(trainer.getActiveMonster()).isEqualTo(waterMonster)
  }

  @Test
  fun `round ends, because trainerOne has no Monsters left`() {
    val trainer = TrainerInBattle(
      "TrainerOne",
      listOf(defeatedMonster, defeatedMonster),
      defeatedMonster,
      3
    )

    val trainerTwo = TrainerInBattle(
      "TrainerOne",
      listOf(defeatedMonster, fireMonster),
      fireMonster,
      3
    )
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = trainer,
      trainerTwo = trainerTwo,
      true
    )

    val choiceTrainerOne = TrainerChoice.SwitchChoice(fireMonster, waterMonster)
    val choiceTrainerTwo = TrainerChoice.SurrenderChoice(trainerTwo)


    battle.submitChoice(trainer, choiceTrainerOne)
    battle.submitChoice(trainerTwo, choiceTrainerTwo)

    assertThat(trainer.getActiveMonster()).isEqualTo(waterMonster)
  }

  @Test
  fun `trainer One is winner`() {

    val baseStats = BaseStats(
      healthPoints = 250,
      initiative = 10,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
    )
    val battleStatsLowHp = BattleStats(
      healthPoints = 2,
      initiative = 10,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
      statusEffect = null
    )

    val monsterLowHp = Monster(
      "AlmostDefeatedMonster",
      Type.NORMAL,
      baseStats,
      battleStatsLowHp,
      listOf(physicalAttackTackle)
    )
    val trainer = TrainerInBattle(
      "TrainerOne",
      listOf(waterMonster, fireMonster),
      fireMonster,
      3
    )

    val trainerTwo = TrainerInBattle(
      "TrainerOne",
      listOf(defeatedMonster, monsterLowHp),
      monsterLowHp,
      3
    )
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = trainer,
      trainerTwo = trainerTwo,
      true
    )

    val choiceTrainerOne = TrainerChoice.AttackChoice(
      fireMonster,
      physicalAttackTackle,
      monsterLowHp
    )
    val choiceTrainerTwo = TrainerChoice.AttackChoice(
      monsterLowHp,
      physicalAttackTackle,
      fireMonster
    )


    battle.submitChoice(trainer, choiceTrainerOne)
    battle.submitChoice(trainerTwo, choiceTrainerTwo)

    assertThat(battle.getWinner()).isEqualTo(trainer)
  }

  @Test
  fun`active monster of trainerTwo gets switched, because it gets defeated`(){
    val baseStats = BaseStats(
      healthPoints = 250,
      initiative = 10,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
    )
    val battleStatsLowHp = BattleStats(
      healthPoints = 2,
      initiative = 10,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
      statusEffect = null
    )

    val monsterLowHp = Monster(
      "AlmostDefeatedMonster",
      Type.NORMAL,
      baseStats,
      battleStatsLowHp,
      listOf(physicalAttackTackle)
    )
    val trainer = TrainerInBattle(
      "TrainerOne",
      listOf(waterMonster, fireMonster),
      fireMonster,
      3
    )

    val trainerTwo = TrainerInBattle(
      "TrainerOne",
      listOf(waterMonster, monsterLowHp),
      monsterLowHp,
      3
    )
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = trainer,
      trainerTwo = trainerTwo,
      true
    )

    val choiceTrainerOne = TrainerChoice.AttackChoice(
      fireMonster,
      physicalAttackTackle,
      monsterLowHp
    )
    val choiceTrainerTwo = TrainerChoice.AttackChoice(
      monsterLowHp,
      physicalAttackTackle,
      fireMonster
    )


    battle.submitChoice(trainer, choiceTrainerOne)
    battle.submitChoice(trainerTwo, choiceTrainerTwo)

    assertThat(battle.getTrainerTwo().getActiveMonster()).isEqualTo(waterMonster)
  }

  @Test
  fun `trainer Two is winner`() {
    val baseStats = BaseStats(
      healthPoints = 250,
      initiative = 10,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
    )
    val battleStatsLowHp = BattleStats(
      healthPoints = 2,
      initiative = 10,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
      statusEffect = null
    )

    val monsterLowHp = Monster(
      "AlmostDefeatedMonster",
      Type.NORMAL,
      baseStats,
      battleStatsLowHp,
      listOf(physicalAttackTackle)
    )
    val trainer = TrainerInBattle(
      "TrainerOne",
      listOf(waterMonster, fireMonster),
      fireMonster,
      3
    )

    val trainerLowHp = TrainerInBattle(
      "TrainerOne",
      listOf(defeatedMonster, monsterLowHp),
      monsterLowHp,
      3
    )
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = trainerLowHp,
      trainerTwo = trainer,
      true
    )

    val choiceTrainerLowHp = TrainerChoice.AttackChoice(
      monsterLowHp,
      physicalAttackTackle,
      fireMonster
    )

    val choiceTrainer = TrainerChoice.AttackChoice(
      fireMonster,
      physicalAttackTackle,
      monsterLowHp
    )


    battle.submitChoice(trainerLowHp, choiceTrainerLowHp)
    battle.submitChoice(trainer, choiceTrainer)

    assertThat(battle.getWinner()).isEqualTo(trainer)
  }

  @Test
  fun `active Monster of trainer One gets switched because its defeated`(){
    val baseStats = BaseStats(
      healthPoints = 250,
      initiative = 10,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
    )
    val battleStatsLowHp = BattleStats(
      healthPoints = 2,
      initiative = 10,
      attack = 100,
      defense = 100,
      specialAttack = 120,
      specialDefense = 120,
      statusEffect = null
    )

    val monsterLowHp = Monster(
      "AlmostDefeatedMonster",
      Type.NORMAL,
      baseStats,
      battleStatsLowHp,
      listOf(physicalAttackTackle)
    )
    val trainer = TrainerInBattle(
      "TrainerOne",
      listOf(waterMonster, fireMonster),
      fireMonster,
      3
    )

    val trainerLowHp = TrainerInBattle(
      "TrainerOne",
      listOf(waterMonster, monsterLowHp),
      monsterLowHp,
      3
    )
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = trainerLowHp,
      trainerTwo = trainer,
      true
    )

    val choiceTrainerLowHp = TrainerChoice.AttackChoice(
      monsterLowHp,
      physicalAttackTackle,
      fireMonster
    )

    val choiceTrainer = TrainerChoice.AttackChoice(
      fireMonster,
      physicalAttackTackle,
      monsterLowHp
    )


    battle.submitChoice(trainerLowHp, choiceTrainerLowHp)
    battle.submitChoice(trainer, choiceTrainer)

    assertThat(trainerLowHp.getActiveMonster()).isEqualTo(waterMonster)
  }
}
