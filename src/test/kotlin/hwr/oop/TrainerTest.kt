package hwr.oop.classes

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class TrainerTest : AnnotationSpec() {

    @Test
    fun `test Trainer creation without Monsters`() {
        val trainer = Trainer(name = "Ash")

        assertThat(trainer.name).isEqualTo("Ash")
        assertThat(trainer.monsters).isEmpty()
        assertThat(trainer.activeMonster).isNull()
    }

    @Test
    fun `test adding Monsters to Trainer`() {
        val trainer = Trainer(name = "Brock")

        val monster = Monster(
            name = "Flamix",
            type = Type.FIRE,
            stats = Stats(100, 100, 10, 20, 15, 25, 20),
            attacks = listOf()
        )

        val success = trainer.addMonster(monster)

        assertThat(success).isTrue()
        assertThat(trainer.monsters).hasSize(1)
        assertThat(trainer.activeMonster).isEqualTo(monster)
    }

    @Test
    fun `test Trainer cannot have more than 6 Monsters`() {
        val trainer = Trainer(name = "Misty")

        val dummyMonster = Monster(
            name = "Dummy",
            type = Type.WATER,
            stats = Stats(100, 100, 10, 10, 10, 10, 10),
            attacks = listOf()
        )

        repeat(6) {
            //val success = trainer.addMonster(dummyMonster.copy(name = "Dummy$it"))
            //assertThat(success).isTrue()
        }

        //val seventh = trainer.addMonster(dummyMonster.copy(name = "TooMuch"))
        //assertThat(seventh).isFalse()
        assertThat(trainer.monsters).hasSize(6)
    }
}
