package hwr.oop.monsterleague.gamelogic.attacks

import hwr.oop.monsterleague.gamelogic.Type

interface Attack {
  val name: String
  val kind: AttackKinds
  val type: Type
  val accuracy: Int
  val power: Int
  val powerPoints: Int

  fun defenderStatChange(): StatChange? = null
  fun attackerStatChange(): StatChange? = null

  fun isSpecial(): Boolean {
    return kind == AttackKinds.SPECIAL
  }

  object VineWhip : Attack {
    override val name = "Vine Whip"
    override val kind = AttackKinds.PHYSICAL
    override val type = Type.GRASS
    override val accuracy = 100
    override val power = 45
    override val powerPoints = 25
  }

  object RazorLeaf : Attack {
    override val name = "Razor Leaf"
    override val kind = AttackKinds.PHYSICAL
    override val type = Type.GRASS
    override val accuracy = 95
    override val power = 55
    override val powerPoints = 25
  }

  object SolarBeam : Attack {
    override val name = "Solar Beam"
    override val kind = AttackKinds.SPECIAL
    override val type = Type.GRASS
    override val accuracy = 100
    override val power = 120
    override val powerPoints = 10
  }

  object FireFang : Attack {
    override val name = "Fire Fang"
    override val kind = AttackKinds.PHYSICAL
    override val type = Type.FIRE
    override val accuracy = 95
    override val power = 65
    override val powerPoints = 15
  }

  object Ember : Attack {
    override val name = "Ember"
    override val kind = AttackKinds.SPECIAL
    override val type = Type.FIRE
    override val accuracy = 100
    override val power = 40
    override val powerPoints = 25
  }

  object Flamethrower : Attack {
    override val name = "Flamethrower"
    override val kind = AttackKinds.SPECIAL
    override val type = Type.FIRE
    override val accuracy = 100
    override val power = 90
    override val powerPoints = 15
  }

  object WaterGun : Attack {
    override val name = "Water Gun"
    override val kind = AttackKinds.SPECIAL
    override val type = Type.WATER
    override val accuracy = 100
    override val power = 40
    override val powerPoints = 25
  }

  object HydroPump : Attack {
    override val name = "Hydro Pump"
    override val kind = AttackKinds.SPECIAL
    override val type = Type.WATER
    override val accuracy = 85
    override val power = 110
    override val powerPoints = 5
  }

  object AquaTail : Attack {
    override val name = "Aqua Tail"
    override val kind = AttackKinds.PHYSICAL
    override val type = Type.WATER
    override val accuracy = 90
    override val power = 90
    override val powerPoints = 10
  }

  object Scratch : Attack {
    override val name = "Scratch"
    override val kind = AttackKinds.PHYSICAL
    override val type = Type.NORMAL
    override val accuracy = 100
    override val power = 40
    override val powerPoints = 35
  }

  object Tackle : Attack {
    override val name = "Tackle"
    override val kind = AttackKinds.PHYSICAL
    override val type = Type.NORMAL
    override val accuracy = 100
    override val power = 40
    override val powerPoints = 35
  }

  object Swift : Attack {
    override val name = "Swift"
    override val kind = AttackKinds.SPECIAL
    override val type = Type.NORMAL
    override val accuracy = 100
    override val power = 60
    override val powerPoints = 20
  }

  object Lick : Attack {
    override val name = "Lick"
    override val kind = AttackKinds.PHYSICAL
    override val type = Type.GHOST
    override val accuracy = 100
    override val power = 30
    override val powerPoints = 30
  }

  object Burden : Attack {
    override val name = "Burden"
    override val kind = AttackKinds.SPECIAL
    override val type = Type.GHOST
    override val accuracy = 100
    override val power = 65
    override val powerPoints = 10
  }

  object ShadowBall : Attack {
    override val name = "Shadow Ball"
    override val kind = AttackKinds.SPECIAL
    override val type = Type.GHOST
    override val accuracy = 100
    override val power = 80
    override val powerPoints = 15
  }
}