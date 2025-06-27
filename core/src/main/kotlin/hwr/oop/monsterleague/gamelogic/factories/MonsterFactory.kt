package hwr.oop.monsterleague.gamelogic.factories

import hwr.oop.monsterleague.gamelogic.BaseStats
import hwr.oop.monsterleague.gamelogic.BattleStats
import hwr.oop.monsterleague.gamelogic.Monster
import hwr.oop.monsterleague.gamelogic.Type
import hwr.oop.monsterleague.gamelogic.attacks.Attack

object MonsterFactory {
  fun create(name: String): Monster {
    return when (name.lowercase()) {
      "bulbasaur" -> Monster(
        name = "Bulbasaur",
        type = Type.GRASS,
        baseStats = BaseStats(
          healthPoints = 35,
          attack = 55,
          defense = 40,
          specialAttack = 65,
          specialDefense = 65,
          initiative = 45
        ),
        battleStats = BattleStats(
          healthPoints = 35,
          attack = 55,
          defense = 40,
          specialAttack = 65,
          specialDefense = 65,
          initiative = 45,
          statusEffect = null
        ),
        attacks = listOf(Attack.VineWhip, Attack.RazorLeaf, Attack.SolarBeam)
      )

      "charmander" -> Monster(
        name = "Charmander",
        type = Type.FIRE,
        baseStats = BaseStats(
          healthPoints = 39,
          attack = 52,
          defense = 43,
          specialAttack = 60,
          specialDefense = 50,
          initiative = 65
        ),
        battleStats = BattleStats(
          healthPoints = 39,
          attack = 52,
          defense = 43,
          specialAttack = 60,
          specialDefense = 50,
          initiative = 65,
          statusEffect = null
        ),
        attacks = listOf(
          Attack.Ember, Attack.Flamethrower, Attack.FireFang
        )
      )

      "squirtle" -> Monster(
        name = "Squirtle",
        type = Type.WATER,
        baseStats = BaseStats(
          healthPoints = 44,
          attack = 48,
          defense = 65,
          specialAttack = 50,
          specialDefense = 64,
          initiative = 43
        ),
        battleStats = BattleStats(
          healthPoints = 44,
          attack = 48,
          defense = 65,
          specialAttack = 50,
          specialDefense = 64,
          initiative = 43,
          statusEffect = null
        ),
        attacks = listOf(Attack.WaterGun, Attack.HydroPump, Attack.AquaTail)
      )

      "gastly" -> Monster(
        name = "Gastly",
        type = Type.GHOST,
        baseStats = BaseStats(
          healthPoints = 30,
          attack = 35,
          defense = 30,
          specialAttack = 100,
          specialDefense = 35,
          initiative = 80
        ),
        battleStats = BattleStats(
          healthPoints = 30,
          attack = 35,
          defense = 30,
          specialAttack = 100,
          specialDefense = 35,
          initiative = 80,
          statusEffect = null
        ),
        attacks = listOf(Attack.Burden, Attack.Lick, Attack.ShadowBall)
      )

      "eevee" -> Monster(
        name = "Eevee",
        type = Type.NORMAL,
        baseStats = BaseStats(
          healthPoints = 55,
          attack = 55,
          defense = 50,
          specialAttack = 45,
          specialDefense = 65,
          initiative = 55
        ),
        battleStats = BattleStats(
          healthPoints = 55,
          attack = 55,
          defense = 50,
          specialAttack = 45,
          specialDefense = 65,
          initiative = 55,
          statusEffect = null
        ),
        attacks = listOf()
      )


      else -> throw IllegalArgumentException("Unknown monster: $name")
    }
  }
}