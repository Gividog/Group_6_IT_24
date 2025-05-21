package monsterleague

import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.BaseStats
import monsterleague.gamelogic.BattleStats
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Status
import monsterleague.gamelogic.Type
import monsterleague.gamelogic.attacks.PhysicalAttack
import org.assertj.core.api.Assertions.assertThat

class MonsterTest : AnnotationSpec() {
    val dummyType1 = Type.GHOST
    val dummyType2 = Type.WATER

    private val dummyAttack =  PhysicalAttack("Punch", dummyType1, 100, 35, 10)

    private var dummyStatus = Status.CONFUSED

    private var dummyBaseStats = BaseStats(
        hp = 100,
        initiative = 10,
        attack = 20,
        defense = 30,
        specialAttack = 30,
        specialDefense = 40,
    )

    private var dummyBattleStats = BattleStats(
        hp = 100,
        initiative = 10,
        attack = 20,
        defense = 30,
        statusEffect = dummyStatus,
        specialAttack = 30,
        specialDefense = 40,
        buff = null,
        debuff = null,
    )

    private var dummyBattleStats2 = BattleStats(
        hp = 0,
        initiative = 10,
        attack = 20,
        defense = 30,
        statusEffect = null,
        specialAttack = 30,
        specialDefense = 40,
        buff = null,
        debuff = null,
    )

    private val dummyMonster2 = Monster(
        name = "Monster2",
        type = dummyType2,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack)
    )

    private val dummyMonster3 = Monster(
        name = "Monster3",
        type = dummyType1,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats2,
        attacks = listOf(dummyAttack)
    )

    /**
     * calculateHealingAmount() tests
     */

    @Test
    fun `healMonster() determines a 30 hp healing on a 100 max hp monster`() {
        // TODO
    }

    /**
     * deadMonster() tests
     */

    @Test
    fun `monsterDead() returns true if monster has 0 hp`() {
        val returnValue = dummyMonster3.deadMonster()

        assertThat(returnValue).isEqualTo(true)
    }

    @Test
    fun `monsterDead() returns false if monster has more than 0 hp`() {
        val returnValue = dummyMonster2.deadMonster()

        assertThat(returnValue).isEqualTo(false)
    }
}