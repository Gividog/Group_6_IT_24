package hwr.oop.classes

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class AttackTest : AnnotationSpec() {

    @Test
    fun `test Attack creation`() {
        // Arrange: Define necessary parameters
        val attackName = "Fire Blast"
        val attackType = Type.Fire
        val category = Category.Special
        val power = 90
        val accuracy = 85
        val powerPoint = 20

        // Act: Create an Attack instance
        val attack = Attack(
            name = attackName,
            type = attackType,
            category = category,
            power = power,
            accuracy = accuracy,
            powerPoint = powerPoint
        )

        // Assert: Verify the Attack's properties
        assertThat(attack.name).isEqualTo("Fire Blast")
        assertThat(attack.type).isEqualTo(Type.Fire)
        assertThat(attack.power).isEqualTo(90)
        assertThat(attack.accuracy).isEqualTo(85)
        assertThat(attack.category).isEqualTo(Category.Special)
        assertThat(attack.powerPoint).isEqualTo(20)
    }
}