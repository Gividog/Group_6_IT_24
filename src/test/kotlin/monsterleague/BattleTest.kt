package monsterleague

import monsterleague.gamelogic.Attack
import monsterleague.gamelogic.Battle
import monsterleague.gamelogic.BaseStats
import monsterleague.gamelogic.BattleStats
import monsterleague.gamelogic.PhysicalAttack

import io.kotest.core.spec.style.AnnotationSpec
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Trainer
import monsterleague.gamelogic.Type
import org.assertj.core.api.Assertions.assertThat


class BattleTest : AnnotationSpec() {
    val type1 = Type("Water", listOf("Fire"), listOf("Grass"))
    val type2 = Type("Fire", listOf("Grass"), listOf("Water"))
    val type3 = Type("Grass", listOf("Water") , listOf("Fire"))

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

}