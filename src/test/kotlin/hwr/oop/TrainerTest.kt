package hwr.oop
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class TrainerTest : AnnotationSpec() {

    @Test
    fun `test Trainer creation without Monsters`() {
        // Act: Create a Trainer object without providing any monsters
        val trainer = Trainer(name = "Ash")

        // Assert: Verify the Trainer's properties
        assertThat(trainer.name).isEqualTo("Ash")
        assertThat(trainer.monster).isNotNull
        assertThat(trainer.monster).isEmpty()
    }
}