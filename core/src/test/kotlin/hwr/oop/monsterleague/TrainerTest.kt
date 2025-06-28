package hwr.oop.monsterleague

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class TrainerTest : AnnotationSpec() {
  @Test
  fun `getTrainerName returns the correct name`() {
    val trainer = TestData.trainerOutOfCombatAsh

    assertThat(trainer.getTrainerName()).isEqualTo("Ash")
  }

  @Test
  fun `getListOfMonsters returns the correct list of monsters`() {
    val trainer = TestData.trainerOutOfCombatAsh

    assertThat(trainer.getListOfMonsters()).isEqualTo(
      listOf(
        TestData.normalMonster,
        TestData.waterMonster,
        TestData.ghostMonster
      )
    )
  }
}