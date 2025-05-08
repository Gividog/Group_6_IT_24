package monsterleague.gamelogic

class Battle(
    val battleID: Int,
    var round: Int,
    var winner: String?,
    val trainers: List<Trainer>) {

    //Long Term Memory
    var damageTrainer1: Int = 0
    var damageTrainer2: Int = 0

    fun chooseAttack(attackingTrainer: Trainer, defendingTrainer: Trainer, attackIndex: Int) {
        val attackingMonster = attackingTrainer.activeMonster
        val defendingMonster = defendingTrainer.activeMonster
        val attack = attackingMonster.attacks[attackIndex - 1]

        val damage = AttackCalculator(
            attackingMonster = attackingMonster,
            defendingMonster = defendingMonster,
            attack = attack,
            battleStats = attackingMonster.battleStats
        ).calculateDamage()

        if (attackingTrainer == trainers[0]) {
            damageTrainer1 = damage
        } else if (attackingTrainer == trainers[1]) {
            damageTrainer2 = damage
        }
    }

    fun healMonster(trainer : Trainer) {
        val monster = trainer.activeMonster

        //limit the amount of heals a trainer has in a fight
        if (trainer.healsRemaining <= 0) {
            println("${trainer.name} has no heals left!")
            return
        }

        val maxHP = monster.baseStats.hp
        val currentHP = monster.battleStats.currenthp

        val healAmount = (maxHP * 0.3).toInt()
        val newHP = minOf(currentHP + healAmount, maxHP)

        monster.battleStats.currenthp = newHP
        trainer.healsRemaining--

        println("${monster.name} was healed by $healAmount HP!")
        println("${trainer.name} has ${trainer.healsRemaining} heals left.")
    }
}
