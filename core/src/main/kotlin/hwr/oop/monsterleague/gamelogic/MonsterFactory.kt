package hwr.oop.monsterleague.gamelogic

import monsterleague.gamelogic.BaseStats
import monsterleague.gamelogic.BattleStats
import monsterleague.gamelogic.Monster
import monsterleague.gamelogic.Type
import monsterleague.gamelogic.attacks.Attack
/*
object MonsterFactory {
  fun create(name: String): Monster {
    return when (name.lowercase()) {
      "bulbasaur" -> Monster(
        name = "Bulbasaur",
        type = Type.GRASS,
        baseStats = BaseStats(
          healthPoints = 35,
          attack = 55,
          defense = 40),
        battleStats = BattleStats(
          healthPoints = 35),
        attacks = listOf(Attack(
            "Thunder Shock",
            power = 40
          )
        )
      )
      "charmander" -> Monster(
        name = "Charmander",
        type = Type.FIRE,
        baseStats = BaseStats(healthPoints = 39, attack = 52, defense = 43),
        battleStats = BattleStats(healthPoints = 39),
        attacks = listOf(
          _root_ide_package_.monsterleague.gamelogic.attacks.Attack(
            "Ember",
            power = 40
          )
        )
      )
      "squirtle" -> Monster(
        name = "Squirtle",
        type = Type.WATER,
        baseStats = BaseStats(healthPoints = 44, attack = 48, defense = 65),
        battleStats = BattleStats(healthPoints = 44),
        attacks = listOf(
          _root_ide_package_.monsterleague.gamelogic.attacks.Attack(
            "Water Gun",
            power = 40
          )
        )
      )
      else -> throw IllegalArgumentException("Unknown monster: $name")
    }
  }
}*/