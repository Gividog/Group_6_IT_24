package monsterleague

import hwr.oop.monsterleague.TestData
import hwr.oop.monsterleague.gamelogic.TrainerChoice
import hwr.oop.monsterleague.gamelogic.TrainerInBattle
import monsterleague.gamelogic.*

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.attacks.AttackKinds
import org.junit.jupiter.api.Assertions.fail
import org.junit.jupiter.api.assertThrows
import java.util.*

class BattleTest : AnnotationSpec() {
  /**
   * surrender () tests
   */

  @Test
  fun `surrendering trainer causes opponent to win the battle`() {
    val battle = Battle(
      TestData.battleUuid,
      TestData.trainerWithTwoMonsters,
      TestData.trainerWithTwoMonsters,
      true

    )

    battle.testSurrender(battle, TestData.trainerWithTwoMonsters)

    assertThat(battle.getWinner()).isEqualTo(TestData.trainerWithTwoMonsters)
  }

  /**
   * startNextRound() tests
   */

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

    battle.testDetermineWinner(battle)

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

    battle.testDetermineWinner(battle)

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

    battle.testDetermineWinner(battle)

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

    val descendingSortedList = battle.testSortActiveMonsterByInitiative(battle)
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

    battle.testEndRound(battle)

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

    battle.testEndRound(battle)

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

    battle.testEndRound(battle)

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

    battle.testEndRound(battle)

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

    battle.testEndRound(battle)

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
  fun `trainer3 chose physicalAttackTackle`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithTwoMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
      true
    )
    val trainer = TestData.trainerWithTwoMonsters
    val targetTrainer = TestData.trainerWithOneDefeatedMonster
    val attackChoice = TrainerChoice.AttackChoice(
      attackingMonster = trainer.getActiveMonster(),
      selectedAttack = TestData.physicalAttackTackle,
      targetedMonster = TestData.trainerWithOneDefeatedMonster.getActiveMonster()

    )
    val initialHP = targetTrainer.getActiveMonster().getHP()
    battle.trainerChooseAttack(trainer, attackChoice)

    val afterHP = targetTrainer.getActiveMonster().getHP()

    assertThat(afterHP).isLessThan(initialHP)
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
    battle.testSurrender(battle, battle.getTrainerTwo())
    val winner = battle.getWinner()

    assertThat(winner).isEqualTo(battle.getTrainerOne())
  }

  /*
        handleAttack Tests
  */

}

