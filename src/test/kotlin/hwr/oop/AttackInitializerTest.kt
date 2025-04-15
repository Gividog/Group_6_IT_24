package hwr.oop

import hwr.oop.classes.Attack
import hwr.oop.init.AttackInitializer
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class AttackInitializerTest : AnnotationSpec(){
    @Test
    fun `test Attack initialization`(){
        val addAttacks = AttackInitializer.preload()
        
    }
}