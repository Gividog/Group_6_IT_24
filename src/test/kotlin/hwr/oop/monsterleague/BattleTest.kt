package monsterleague

import hwr.oop.monsterleague.TestData
import monsterleague.gamelogic.*

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.attacks.Attack
import monsterleague.gamelogic.attacks.AttackKinds
import monsterleague.gamelogic.attacks.PhysicalAttack
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
      TestData.trainerWithTwoMonsters
    )

    battle.surrender(TestData.trainerWithTwoMonsters)

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
    )

    battle.startNextRound()

    assertThat(battle.getRounds()).isEqualTo(2)
    assertThat(battle.getWinner()).isNull()
    assertThat(battle.getTrainerOne().getActiveMonster()).isEqualTo(TestData.fireMonster)
    assertThat(battle.getTrainerTwo().getActiveMonster()).isEqualTo(TestData.waterMonster)
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
    )

    battle.determineWinner()

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
    )

    battle.determineWinner()

    assertThat(battle.getWinner()).isEqualTo(TestData.trainerWithTwoMonsters)
    assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithOnlyDefeatedMonsters)
  }

  @Test
  fun `second Trainer Is Winner`() {
    val battle = Battle(
      battleID = TestData.battleUuid,
      trainerOne = TestData.trainerWithOnlyDefeatedMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
    )

    battle.determineWinner()

    assertThat(battle.getWinner()).isEqualTo(TestData.trainerWithTwoMonsters)
    assertThat(battle.getWinner()).isNotEqualTo(TestData.trainerWithOnlyDefeatedMonsters)
  }

  @Test
  fun `addChosenAttackToMap()`() {
    // TODO
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
      trainerOne = TestData.trainerWithOnlyDefeatedMonsters,
      trainerTwo = TestData.trainerWithTwoMonsters,
    )

    val descendingSortedList = battle.sortActiveMonstersByInitiative()
    assertThat(descendingSortedList).containsExactly(
      TestData.defeatedMonster,
      TestData.waterMonster,
    )
  }

  @Test
  fun `kind of Attack is Physical`() {
    val kind =
      Battle(TestData.battleUuid, TestData.trainerWithTwoMonsters, TestData.trainerWithOnlyDefeatedMonsters).getKindOfAttack(
        TestData.physicalAttackTackle
      )
    assertThat(kind).isEqualTo(AttackKinds.PHYSICAL)
  }

  @Test
  fun `next active Monster is fireMonster`() {
    Battle(
      TestData.battleUuid,
      trainerOne = TestData.trainerWithOneDefeatedMonster,
      trainerTwo = TestData.trainerWithOneDefeatedMonster,
    ).endRound()

    assertThat(TestData.trainerWithOneDefeatedMonster.getActiveMonster()).isEqualTo(
      TestData.waterMonster
    )
    assertThat(TestData.trainerWithOneDefeatedMonster.getMonsters()).isNotNull()

  }

  @Test
  fun `no monsters left to replace active monsters`() {
    Battle(TestData.battleUuid, listOf(trainerWithDeadActiveMonster, dummyTrainer2)).endRound()

    assertThat(trainerWithDeadActiveMonster.getActiveMonster()).isEqualTo(
      deadDummyMonster
    )
  }

  @Test
  fun `trainer3 chose  dummyAttack `() {
    val battle = Battle(
      battleID = uuid,
      trainers = listOf(dummyTrainer3, dummyTrainer1),
    )

    battle.chooseAttack(dummyTrainer3, dummyAttack)
    val mapOfChosenAttack = battle.getChosenAttackMap()

    assertThat(mapOfChosenAttack).isEqualTo(mapOf(dummyMonster3 to dummyAttack))

  }

  @Test
  fun `simulateRound applies damage in correct initiative order`() {
    val battle = Battle(
      battleID = uuid,
      trainers = listOf(dummyTrainer2, dummyTrainer1)
    )
    val startingHP1 = dummyTrainer1.getActiveMonstersHP()
    val startingHP2 = dummyTrainer2.getActiveMonstersHP()

    battle.chooseAttack(dummyTrainer1, dummyAttack)
    battle.chooseAttack(dummyTrainer2, dummyAttack)

    assertThat(dummyTrainer1.getActiveMonstersHP()).isLessThan(startingHP1)
    assertThat(dummyTrainer2.getActiveMonstersHP()).isLessThan(startingHP2)

    assertThat(battle.getChosenAttackMap()).isEmpty()
  }
}

