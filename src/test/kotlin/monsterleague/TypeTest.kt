package monsterleague

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.Type
import monsterleague.gamelogic.TypeTable

class TypeTest : AnnotationSpec() {

    @Test
    fun `typeTable knows efficiencies and inefficiencies of NORMAL`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.NORMAL)
        val inefficiencies = typeTable.inefficienciesOf(Type.NORMAL)


        assertThat(efficiencies).containsExactly()
        assertThat(inefficiencies).containsExactly(Type.GHOST)

    }

    @Test
    fun `typeTable knows efficiencies and inefficiencies of FIRE`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.FIRE)
        val inefficiencies = typeTable.inefficienciesOf(Type.FIRE)


        assertThat(efficiencies).containsExactly(Type.GRASS)
        assertThat(inefficiencies).containsExactly(Type.WATER, Type.FIRE)

    }

    @Test
    fun `typeTable knows efficiencies and inefficiencies of WATER`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.WATER)
        val inefficiencies = typeTable.inefficienciesOf(Type.WATER)


        assertThat(efficiencies).containsExactly(Type.FIRE)
        assertThat(inefficiencies).containsExactly(Type.GRASS, Type.WATER)

    }

    @Test
    fun `typeTable knows efficiencies and inefficiencies of GRASS`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.GRASS)
        val inefficiencies = typeTable.inefficienciesOf(Type.GRASS)


        assertThat(efficiencies).containsExactly(Type.WATER)
        assertThat(inefficiencies).containsExactly(Type.FIRE, Type.GRASS)

    }

    @Test
    fun `typeTable knows efficiencies and inefficiencies of GHOST`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.GHOST)
        val inefficiencies = typeTable.inefficienciesOf(Type.GHOST)


        assertThat(efficiencies).containsExactly(Type.GHOST)
        assertThat(inefficiencies).containsExactly(Type.NORMAL)

    }

    @Test
    fun`efficiency Of returns empty list when type is UNKNOWN`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.UNKNOWN)
        assertThat(efficiencies).isEmpty()
    }

    @Test
    fun`inefficiencyOf returns empty list when type is UNKNOWN`(){
        val typeTable = TypeTable()
        val inefficiencies = typeTable.inefficienciesOf(Type.UNKNOWN)
        assertThat(inefficiencies).isEmpty()
    }

}