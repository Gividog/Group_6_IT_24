package hwr.oop

import hwr.oop.classes.Monster
import hwr.oop.classes.Attack
import hwr.oop.classes.Category
import hwr.oop.classes.Type
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class MonsterAttackTest : AnnotationSpec() {

    @Test
    fun `test can monster use attack` () {
        val monsterUwe = Monster(attacks = Attack)
        val attackTackle = Attack("Tackle", Type.Normal, Category.Physical, 40, 100, 35 )
    }
}