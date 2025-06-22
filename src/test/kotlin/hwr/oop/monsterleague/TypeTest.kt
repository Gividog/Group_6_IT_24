package monsterleague

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.Type
import monsterleague.gamelogic.TypeTable

class TypeTest : AnnotationSpec() {

  @Test
  fun `typeTable knows efficiencies and inefficiencies of NORMAL`() {
    val typeTable = TypeTable()
    val efficiencies = typeTable.efficiencyOf(Type.NORMAL)
    val inefficiencies = typeTable.inefficiencyOf(Type.NORMAL)

    assertThat(efficiencies).isEmpty()
    assertThat(inefficiencies).containsExactly(Type.GHOST)
  }

  @Test
  fun `typeTable knows efficiencies and inefficiencies of FIRE`() {
    val typeTable = TypeTable()
    val efficiencies = typeTable.efficiencyOf(Type.FIRE)
    val inefficiencies = typeTable.inefficiencyOf(Type.FIRE)

    assertThat(efficiencies).containsExactly(Type.GRASS)
    assertThat(inefficiencies).containsExactly(Type.WATER, Type.FIRE)
  }

  @Test
  fun `typeTable knows efficiencies and inefficiencies of WATER`() {
    val typeTable = TypeTable()
    val efficiencies = typeTable.efficiencyOf(Type.WATER)
    val inefficiencies = typeTable.inefficiencyOf(Type.WATER)

    assertThat(efficiencies).containsExactly(Type.FIRE)
    assertThat(inefficiencies).containsExactly(Type.GRASS, Type.WATER)
  }

  @Test
  fun `typeTable knows efficiencies and inefficiencies of GRASS`() {
    val typeTable = TypeTable()
    val efficiencies = typeTable.efficiencyOf(Type.GRASS)
    val inefficiencies = typeTable.inefficiencyOf(Type.GRASS)

    assertThat(efficiencies).containsExactly(Type.WATER)
    assertThat(inefficiencies).containsExactly(Type.FIRE, Type.GRASS)
  }

  @Test
  fun `typeTable knows efficiencies and inefficiencies of GHOST`() {
    val typeTable = TypeTable()
    val efficiencies = typeTable.efficiencyOf(Type.GHOST)
    val inefficiencies = typeTable.inefficiencyOf(Type.GHOST)

    assertThat(efficiencies).containsExactly(Type.GHOST)
    assertThat(inefficiencies).containsExactly(Type.NORMAL)
  }
}