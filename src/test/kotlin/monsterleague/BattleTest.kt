package monsterleague

import monsterleague.gamelogic.Attack
import monsterleague.gamelogic.Battle
import monsterleague.gamelogic.BaseStats
import monsterleague.gamelogic.BattleStats
import monsterleague.gamelogic.PhysicalAttack
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Trainer
import monsterleague.gamelogic.Type

import org.assertj.core.api.Assertions.assertThat
import io.kotest.core.spec.style.AnnotationSpec

class BattleTest : AnnotationSpec() {
    val type1 = Type.GHOST
    val type2 = Type.WATER
    val type3 = Type.FIRE

    private val dummyAttack = Attack(
        physicalAttack = PhysicalAttack("Punch", type1, 100, 35,10)
    )

    private val dummyBaseStats = BaseStats(
        hp = 100,
        initiative = 10,
        buff = 5,
        debuff = 10
    )

    private val dummyBattleStats = BattleStats(
        currenthp = 50,
        statusEffect = 2,
        buffActive = false,
        debuffActive = false

    )
    private val monster1 = Monster(
        name = "Monster1",
        type = type1,
        status = 1,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack),
    )

    private val monster2 = Monster(
        name = "Monster2",
        type = type2,
        status = 2,
        baseStats = dummyBaseStats,
        battleStats = dummyBattleStats,
        attacks = listOf(dummyAttack)
    )

    private val trainer1 = Trainer("trainer1", listOf(monster1, monster2), monster2, 3)
    private val trainer2 = Trainer("trainer2", listOf(monster1, monster2), monster1, 3)

    /**
     * Initial Values/Variables tests
     */

    /**
     * chooseAttack tests
     */

    @Test
    fun `attackingMonster, defendingMonster and attack are declared correctly`() {

        val attackingTrainer = trainer1
        val defendingTrainer = trainer2
        val battle = Battle(1,1,"",listOf(trainer1,trainer2))

       trainer1.chooseAttack(1)

        val attackingMonster = attackingTrainer.activeMonster
        val defendingMonster = defendingTrainer.activeMonster
        val chosenAttack = attackingMonster.attacks[0]

        assertThat(attackingMonster).isEqualTo(monster2)
        assertThat(defendingMonster).isEqualTo(monster1)
        assertThat(chosenAttack).isEqualTo(dummyAttack)
    }

    // TODO : AttackCalculator schreiben
    /*@Test
    fun `chooseAttack should set damageTrainer1 correctly`() {
        val battle = Battle(
            battleID = 1,
            round = 1,
            winner = null,
            trainers = listOf(trainer1, trainer2),
        )
        battle.chooseAttack(attackingTrainer = trainer1, defendingTrainer = trainer2, attackIndex = 1)
        assertThat(battle.damageTrainer1).isEqualTo(100)
        assertThat(battle.damageTrainer2).isEqualTo(0)
    }

    @Test
    fun `chooseAttack should set damageTrainer2 correctly`() {
        val battle = Battle(
            battleID = 1,
            round = 1,
            winner = null,
            trainers = listOf(trainer1, trainer2),
        )
        battle.chooseAttack(attackingTrainer = trainer2, defendingTrainer = trainer1, attackIndex = 1)
        val damage = 100
        assertThat(battle.damageTrainer2).isEqualTo(100)
        assertThat(battle.damageTrainer1).isEqualTo(0)
    }*/
}

