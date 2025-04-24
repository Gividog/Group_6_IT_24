package hwr.oop.classes
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class TypeTest : AnnotationSpec() {

    @Test
    fun `test Type creation`() {

        // Act: Create a Type instance
        val type = Type.FIRE

        // Assert: Verify the Type's property
        assertThat(type.name).isEqualTo("FIRE")
    }
}