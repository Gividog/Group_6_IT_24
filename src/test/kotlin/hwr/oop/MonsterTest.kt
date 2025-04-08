package hwr.oop

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MonsterTest : AnnotationSpec() {
    @Test
    fun `monster is called Uwe`() {
        val name = "Uwe"
        val monster = Monster(name)
        assertThat(monster.name).isEqualTo(name)
    }
}