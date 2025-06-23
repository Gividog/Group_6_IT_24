package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.TrainerInBattle
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat


class TrainerInBattleTest : AnnotationSpec() {

  @Test
  fun `setHealsRemaining updates the internal state correctly`() {
    val trainer = TestData.trainerWithTwoMonsters
    trainer.setHealsRemaining(1)
    assertThat(trainer.getHealsRemaining()).isEqualTo(1)
  }

  @Test
  fun `setMonsters replaces monster lost using TestData trainer`() {
    val trainer = TestData.trainerWithTwoMonsters
    val newMonsters = listOf(TestData.ghostMonster, TestData.normalMonster)

    trainer.setMonsters(newMonsters)
    val result = trainer.getMonsters()

    assertThat(result).hasSize(2)
    assertThat(result[0].getName()).isEqualTo("Monster3")
    assertThat(result[1].getName()).isEqualTo("Monster4")
  }

  @Test
  fun `setActiveMonster changes active monster using testData trainer`() {
    val trainer = TestData.trainerWithTwoMonsters
    trainer.setActiveMonster(TestData.normalMonster)

    assertThat(trainer.getActiveMonster().getName()).isEqualTo("Monster4")
  }

  @Test
  fun `setNotReadyToFight sets readyToFight to false using TestData trainer`() {
    val trainer = TestData.trainerWithTwoMonsters
    trainer.setNotReadyToFight()

    assertThat(trainer.getReadyToFight()).isFalse

  }

  @Test
  fun `trainers Name is trainer1 `() {
    val trainer = TestData.trainerWithTwoMonsters
    val nameOfTrainer = trainer.getName()
    assertThat(nameOfTrainer).isEqualTo("trainer1")
  }

  @Test
  fun `checkActiveMonsterDefeated() returns true if the active monster is defeated`() {
    val trainer = TestData.trainerWithOneDefeatedMonster

    assertThat(trainer.checkActiveMonsterDefeated()).isTrue()
  }

  @Test
  fun `checkActiveMonsterDefeated() returns false if the active monster is not defeated`() {
    val trainer = TestData.trainerWithTwoMonsters

    assertThat(trainer.checkActiveMonsterDefeated()).isFalse()
  }
}