package hwr.oop
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class TypeTest : AnnotationSpec() {

    @Test
    fun `test Type creation`() {
        // Arrange: Define the type name
        val typeName = "Fire"

        // Act: Create a Type instance
        val type = Type(name = typeName)

        // Assert: Verify the Type's property
        assertThat(type.name).isEqualTo("Fire")
    }
}