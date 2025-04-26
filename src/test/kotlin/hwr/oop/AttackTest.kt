package hwr.oop.classes


import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.doubles.shouldBeGreaterThanOrEqual
import io.kotest.matchers.doubles.shouldBeLessThanOrEqual
import io.kotest.matchers.should
import org.assertj.core.api.Assertions.assertThat
import io.kotest.matchers.shouldBe
import io.kotest.matchers.maps.shouldContainKey
import org.assertj.core.api.Assertions.`as`

class AttackTest : AnnotationSpec() {

    val attackOne = Attack(type = Type.FIRE,
        category = Category.SPECIAL,
        attackSpecificData = AttackSpecificData(name = "Fire Ball",
            power = 95,
            accuracy = 100,
            powerPoint = 20))

    val attackTwo = Attack(type = Type.WATER, category = Category.PHYSICAL, attackSpecificData = AttackSpecificData(
        name = "Surf",
        power = 95,
        accuracy = 100,
        powerPoint = 15))

    val attackThree = Attack(type = Type.GRASS, category = Category.STATUS, attackSpecificData = AttackSpecificData(
        name = "Vine Whip",
        power = 45,
        accuracy = 100,
        powerPoint = 25))

    val attackGround = Attack(type = Type.GROUND, category = Category.STATUS, attackSpecificData = AttackSpecificData(
        name = "Sand Attack",
        power = 0,
        accuracy = 100,
        powerPoint = 15))

    val attackElectric = Attack(type = Type.ELECTRIC, category = Category.PHYSICAL, attackSpecificData = AttackSpecificData(
        name = "Thunder Punch",
        power = 75,
        accuracy = 100,
        powerPoint = 15))

    val attackPsychic = Attack(type = Type.PSYCHIC, category = Category.SPECIAL, attackSpecificData = AttackSpecificData(
        name = "Psybeam",
        power = 65,
        accuracy = 100,
        powerPoint = 20))

    val attackNormal = Attack(type = Type.NORMAL, category = Category.PHYSICAL, attackSpecificData = AttackSpecificData(
        name = "Pound",
        power = 40,
        accuracy = 100,
        powerPoint = 35))

    val listOfAttacks = listOf(attackOne, attackTwo, attackThree)
    val listOfAttacksTwo = listOf(attackPsychic,attackElectric,attackGround)
    val listOfAttacksThree = listOf(attackNormal, attackTwo, attackElectric)

    val monsterFire = Monster(type = Type.FIRE,
        name = "Glumanda",
        stats = Stats(hp = 100,
            currenthp = 60,
            initiative = 20,
            attack = 10,
            defense = 5,
            specialAttack = 2,
            specialDefense = 1),
        attacks = listOfAttacks)

    val monsterWater = Monster(type = Type.WATER,
        name = "Alomomola",
        stats = Stats(hp = 100,
            currenthp = 80,
            initiative = 20,
            attack = 10,
            defense = 5,
            specialAttack = 2,
            specialDefense = 1),
        attacks = listOfAttacks)

    val monsterGround = Monster(type = Type.GROUND,
        name = "Knacklion",
        stats = Stats(hp = 100,
            currenthp = 40,
            initiative = 10,
            attack = 9,
            defense = 5,
            specialAttack = 6,
            specialDefense = 2),
        attacks = listOfAttacksThree)

    val monsterElectric = Monster(type = Type.ELECTRIC,
        name = "Voltenso",
        stats = Stats(hp = 100,
            currenthp = 56,
            initiative = 20,
            attack = 10,
            defense = 5,
            specialAttack =5,
            specialDefense = 2),
        attacks = listOfAttacks)

    val monsterPsychic = Monster(type = Type.PSYCHIC,
        name = "Simsala",
        stats = Stats(hp = 100,
            currenthp = 96,
            initiative = 10,
            attack = 20,
            defense = 15,
            specialAttack =10,
            specialDefense = 15),
        attacks = listOfAttacksTwo)

    val monsterNormal = Monster(type = Type.NORMAL,
        name = "Relaxo",
        stats = Stats(hp = 100,
            currenthp = 80,
            initiative = 10,
            attack = 10,
            defense = 5,
            specialAttack =5,
            specialDefense = 2),
        attacks = listOfAttacks)

    val monsterGrass = Monster(type = Type.GRASS,
        name = "Ogerpon",
        stats = Stats(hp = 100,
            currenthp = 56,
            initiative = 20,
            attack = 10,
            defense = 5,
            specialAttack =5,
            specialDefense = 2),
        attacks = listOfAttacksThree)


    @Test
    fun `test Attack creation`() {

        assertThat(attackOne.attackSpecificData.name).isEqualTo("Fire Ball")
        assertThat(attackOne.type).isEqualTo(Type.FIRE)
        assertThat(attackOne.attackSpecificData.power).isEqualTo(95)
        assertThat(attackOne.attackSpecificData.accuracy).isEqualTo(100)
        assertThat(attackOne.category).isEqualTo(Category.SPECIAL)
        assertThat(attackOne.attackSpecificData.powerPoint).isEqualTo(20)
    }

    @Test
    fun `test list of attacks`(){
        val listAttacks = ListOfAttacks(attacks = mapOf("Surf" to attackTwo))
        assertThat(listAttacks.attacks["Surf"]?.attackSpecificData?.power).isEqualTo(95)
        assertThat(listAttacks.attacks["Surf"]?.attackSpecificData?.accuracy).isEqualTo(100)
        assertThat(listAttacks.attacks["Surf"]?.attackSpecificData?.powerPoint).isEqualTo(15)
        assertThat(listAttacks.attacks["Surf"]?.type).isEqualTo(Type.WATER)
        assertThat(listAttacks.attacks["Surf"]?.category).isEqualTo(Category.PHYSICAL)
    }

    @Test
    fun `stab factor based on type of attack and type of monster `(){

        val stabFactors = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterWater, chosenAttackNumber = 1).calculateValueOfStab()
        assertThat(stabFactors).containsExactly(1.5,1.0,1.0)
    }

    @Test
    fun `test calculate new amount of Power Point left`(){

        assertThat(monsterFire.attacks[2].attackSpecificData.powerPoint ).isEqualTo(25)
        CalculateAttackValues(attackingMonster = monsterFire , defendingMonster = monsterWater, chosenAttackNumber = 2).calculateLeftAmountOfAttack()
        assertThat(monsterFire.attacks[2].attackSpecificData.powerPoint ).isEqualTo(24)
    }

    @Test
    fun `test if random factor for damage calculation is valid`(){
        val randomFactorForDamageCalculation = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterWater, chosenAttackNumber = 1 ).calculateRandomNumberForDamageCalculation()
        assertThat(randomFactorForDamageCalculation).isGreaterThanOrEqualTo(0.85)
        assertThat(randomFactorForDamageCalculation).isLessThanOrEqualTo(1.00)
    }

    @Test
    fun `test Fire Attack against Water Monster`(){
        val attackingMonster = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterWater, chosenAttackNumber = 0)
        val typeEffectivenessValue = attackingMonster.getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValue).isEqualTo(0.5)
        assertThat(attackingMonster.attackingMonster.attacks[0].type).isEqualTo(Type.FIRE)

    }

    @Test
    fun `test Fire Attack against Fire Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterFire, chosenAttackNumber = 0).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(0.5)
    }

    @Test
    fun `test Fire Attack against Ground Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterGround, chosenAttackNumber = 0).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(1.0)
    }

    @Test
    fun `test Fire Attack against Electric Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterElectric, chosenAttackNumber = 0).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(1.0)
    }

    @Test
    fun `test Fire Attack against Psychic Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterPsychic, chosenAttackNumber = 0).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(1.0)
    }

    @Test
    fun `Fire Attack against Normal Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterNormal, chosenAttackNumber = 0).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(1.0)
    }

    @Test
    fun `test Fire Attack against Grass Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterFire, defendingMonster = monsterGrass, chosenAttackNumber = 0).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(2.0)
    }

    @Test
    fun `test Water Attack against Water Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterWater, defendingMonster = monsterWater, chosenAttackNumber = 1).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(0.5)
    }

    @Test
    fun `test Water Attack against Fire Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterWater, defendingMonster = monsterFire, chosenAttackNumber = 1).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(2.0)
    }

    @Test
    fun `test Water Attack against Ground Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterWater, defendingMonster = monsterGround, chosenAttackNumber = 1).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(2.0)
    }

    @Test
    fun `test Water Attack against Electric Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterWater, defendingMonster = monsterElectric, chosenAttackNumber = 1).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(1.0)
    }

    @Test
    fun `test Water Attack against Psychic Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterWater, defendingMonster = monsterPsychic, chosenAttackNumber = 1).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(1.0)
    }

    @Test
    fun `test Water Attack against Normal Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterWater, defendingMonster = monsterNormal, chosenAttackNumber = 1).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(1.0)
    }

    @Test
    fun `test Water Attack against Grass Monster`(){
        val typeEffectivenessValueFire = CalculateAttackValues(attackingMonster = monsterWater, defendingMonster = monsterGrass, chosenAttackNumber = 1).getValueOfTypeEffectiveness()
        assertThat(typeEffectivenessValueFire).isEqualTo(0.5)
    }


}