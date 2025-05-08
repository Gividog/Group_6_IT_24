package monsterleague

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.Type

class TypeTest : AnnotationSpec(){
    @Test
    fun `Type Water has efficient Type against Fire and Ground and inefficient Type against Grass`() {
        assertThat(Type.WATER.efficiency).contains("FIRE","GROUND")
        assertThat(Type.WATER.inefficiency).contains("WATER","GRASS")
    }

    @Test
    fun `Type FIRE is efficient against GRASS and inefficient against WATER`(){
        assertThat(Type.FIRE.efficiency).contains("GRASS")
        assertThat(Type.FIRE.inefficiency).contains("WATER", "FIRE")

    }

    @Test
    fun `Type GROUND is efficient against Type FIRE and inefficient against Type WATER`(){
        assertThat(Type.GROUND.efficiency).contains("FIRE", "ELECTRIC")
        assertThat(Type.GROUND.inefficiency).contains("WATER", "GROUND", "GRASS")
    }

    @Test
    fun `Type GRASS is efficient against WATER and inefficient against FIRE`() {
        assertThat(Type.GRASS.efficiency).contains("WATER", "GROUND")
        assertThat(Type.GRASS.inefficiency).contains("FIRE", "GRASS")
    }

    @Test
    fun `Type ELECTRIC is efficient against Type WATER and inefficient against Type GROUND, ELECTRIC and GRASS` () {
        assertThat(Type.ELECTRIC.efficiency).contains("WATER")
        assertThat(Type.ELECTRIC.inefficiency).contains("GROUND", "ELECTRIC", "GRASS")
    }
}