package hwr.oop.monsterleague

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class TrainerTest : AnnotationSpec() {
  @Test
  fun `getTrainerName returns the correct name`() {
    val trainer = TestData.trainerOutOfCombat

    assertThat(trainer.getTrainerName()).isEqualTo("trainer5")
  }

  @Test
  fun `getTrainerName returns the correct list of monsters`() {
    val trainer = TestData.trainerOutOfCombat

    assertThat(trainer.getListOfMonsters()).isEqualTo(
      listOf(
        TestData.normalMonster,
        TestData.waterMonster,
        TestData.ghostMonster
      )
    )
  }
}