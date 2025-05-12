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
        val balances = typeTable.balancesOf(Type.NORMAL)

        assertThat(efficiencies).containsExactly()
        assertThat(inefficiencies).containsExactly(Type.GHOST)
        assertThat(balances).containsExactly(Type.NORMAL, Type.FIRE, Type.WATER, Type.GRASS)
    }

    @Test
    fun `typeTable knows efficiencies and inefficiencies of FIRE`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.FIRE)
        val inefficiencies = typeTable.inefficienciesOf(Type.FIRE)
        val balances = typeTable.balancesOf(Type.FIRE)

        assertThat(efficiencies).containsExactly(Type.GRASS)
        assertThat(inefficiencies).containsExactly(Type.WATER, Type.FIRE)
        assertThat(balances).containsExactly(Type.GHOST, Type.NORMAL)
    }

    @Test
    fun `typeTable knows efficiencies and inefficiencies of WATER`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.WATER)
        val inefficiencies = typeTable.inefficienciesOf(Type.WATER)
        val balances = typeTable.balancesOf(Type.WATER)

        assertThat(efficiencies).containsExactly(Type.FIRE)
        assertThat(inefficiencies).containsExactly(Type.GRASS, Type.WATER)
        assertThat(balances).containsExactly(Type.NORMAL, Type.GHOST)
    }

    @Test
    fun `typeTable knows efficiencies and inefficiencies of GRASS`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.GRASS)
        val inefficiencies = typeTable.inefficienciesOf(Type.GRASS)
        val balances = typeTable.balancesOf(Type.GRASS)

        assertThat(efficiencies).containsExactly(Type.WATER)
        assertThat(inefficiencies).containsExactly(Type.FIRE, Type.GRASS)
        assertThat(balances).containsExactly(Type.NORMAL, Type.GHOST)
    }

    @Test
    fun `typeTable knows efficiencies and inefficiencies of GHOST`(){
        val typeTable = TypeTable()
        val efficiencies = typeTable.efficiencyOf(Type.GHOST)
        val inefficiencies = typeTable.inefficienciesOf(Type.GHOST)
        val balances = typeTable.balancesOf(Type.GHOST)

        assertThat(efficiencies).containsExactly(Type.GHOST)
        assertThat(inefficiencies).containsExactly(Type.NORMAL)
        assertThat(balances).containsExactly(Type.FIRE, Type.WATER, Type.GRASS)
    }
}