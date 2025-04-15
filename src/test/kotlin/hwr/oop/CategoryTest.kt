package hwr.oop

import hwr.oop.classes.Category
import hwr.oop.classes.Type
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.AnnotationSpec.Test
import org.assertj.core.api.Assertions.assertThat

class CategoryTest : AnnotationSpec(){

    @Test
    fun `test Category creation`() {

        // Act: Create a Category instance
        val category = Category.Special

        // Assert: Verify the Type's property
        assertThat(category.name).isEqualTo("Special")
    }
}