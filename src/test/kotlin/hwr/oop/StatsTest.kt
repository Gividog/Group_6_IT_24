package hwr.oop.classes
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class StatsTest : AnnotationSpec() {

    @Test
    fun `test Stats creation`() {
        // Arrange: Define initial stats values
        val hp = 100
        val speed = 75
        val attack = 50
        val defense = 40
        val spezAttack = 60
        val spezDefense = 55

        // Act: Create a Stats instance
        val stats = Stats(
            hp = hp,
            speed = speed,
            attack = attack,
            defense = defense,
            spezAttack = spezAttack,
            spezDefense = spezDefense
        )

        // Assert: Verify each stat value
        assertThat(stats.hp).isEqualTo(100)
        assertThat(stats.speed).isEqualTo(75)
        assertThat(stats.attack).isEqualTo(50)
        assertThat(stats.defense).isEqualTo(40)
        assertThat(stats.spezAttack).isEqualTo(60)
        assertThat(stats.spezDefense).isEqualTo(55)
    }
}