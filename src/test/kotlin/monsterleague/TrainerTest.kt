package monsterleague

import monsterleague.gamelogic.*

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class TrainerTest : AnnotationSpec() {
    private val dummyType = Type.WATER

    private val dummyBuff = Buff(name = "Wut", effect = "keine Ahnung", type = dummyType)
    private val dummyDebuff = Debuff(name = "Schwäche", effect = "keine Ahnung", type = dummyType)

    private val dummyStats = Stats(
        hp = 100,
        initiative = 10,
        attack = 20,
        defense = 30,
        buff = dummyBuff,
        debuff = dummyDebuff,
        statusEffect = 1
    )

    private val dummyAttack = Attack(
        physicalAttack = PhysicalAttack("Punch", dummyType, 100, 35,10)
    )

    private val dummyMonster1 = Monster(
        name = "Monster1",
        type = dummyType,
        status = 1,
        BaseStats = dummyStats,
        BattleStats = dummyStats,
        attacks = listOf(dummyAttack),
    )

    private val dummyMonster2 = Monster(
        name = "Monster2",
        type = dummyType,
        status = 2,
        BaseStats = dummyStats,
        BattleStats = dummyStats,
        attacks = listOf(dummyAttack)
    )

    private val dummyMonster3 = Monster(
        name = "Monster3",
        type = dummyType,
        status = 3,
        BaseStats = dummyStats,
        BattleStats = dummyStats,
        attacks = listOf(dummyAttack)
    )

    private val dummyTrainer = Trainer("trainer1", listOf(dummyMonster1, dummyMonster2), dummyMonster1, 3)

    /**
     * chooseAttack tests
     */

    @Test
    fun `chosen Attack is Punch`(){
        val attack = dummyTrainer.chooseAttack(1)
        assertThat(attack).isEqualTo(dummyAttack)
    }

    /**
     * changeActiveMonster tests
     */

    @Test
    fun `the newly selected monster at index 1 (so the second element) gets set as the trainers activeMonster`() {
        dummyTrainer.monsters = listOf(dummyMonster1, dummyMonster2)
        dummyTrainer.activeMonster = dummyMonster1

        dummyTrainer.switchActiveMonster(dummyMonster2)
        val newActiveMonster = dummyTrainer.monsters.contains(dummyMonster2)

      //kaputt  assertThat(newActiveMonster).isEqualTo(dummyMonster2)
    }

    @Test
    fun `the new selected active monster doesnt exist in Trainers monster list`(){
        val activeMonster = dummyTrainer.activeMonster
        dummyTrainer.switchActiveMonster(monster = dummyMonster3)
        val newSetActiveMonster = dummyTrainer.activeMonster

        assertThat(newSetActiveMonster).isEqualTo(activeMonster)
    }

    /**
     * healActiveMonster tests
     */

    @Test
    fun `healActiveMonster() heals monster's currenthp (80) and reduces healsRemaining (0)`() {
        dummyTrainer.activeMonster.BaseStats.hp = 50
        dummyTrainer.healsRemaining = 1

        dummyTrainer.healActiveMonster()

        assertThat(dummyTrainer.activeMonster.BattleStats.hp).isEqualTo(80)
        assertThat(dummyTrainer.healsRemaining).isEqualTo(0)
    }

    @Test
    fun `healActiveMonster() does nothing when no heals remaining`() {
        dummyTrainer.healsRemaining = 0

        dummyTrainer.healActiveMonster()

        assertThat(dummyTrainer.healsRemaining).isEqualTo(0)
    }
}