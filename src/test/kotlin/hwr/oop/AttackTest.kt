package hwr.oop.classes

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class AttackTest : AnnotationSpec() {

    @Test
    fun `test Attack creation`() {
        // Arrange: Define necessary parameters
        val attackName = "Fire Blast"
        val attackType = Type.Feuer
        val damage = 90
        val accuracy = 85

        // Act: Create an Attack instance
        val attack = Attack(
            name = attackName,
            type = attackType,
            damage = damage,
            accuracy = accuracy
        )

        // Assert: Verify the Attack's properties
        assertThat(attack.name).isEqualTo("Fire Blast")
        assertThat(attack.type).isEqualTo(attackType)
        assertThat(attack.damage).isEqualTo(90)
        assertThat(attack.accuracy).isEqualTo(85)
    }
}