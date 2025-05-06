package monsterleague.gamelogic

class Battle(
    val battleID: Int,
    var round: Int,
    var winner: String?,
    val trainers: List<Trainer>,
    var chosenAttackTrainer1: Int?,
    var chosenAttackTrainer2: Int?
) {
    fun chooseAttack(AttackingTrainer: Trainer, DefendingTrainer: Trainer, attackIndex: Int ) {
        val attackingMonster = AttackingTrainer.activeMonster
        val defendingMonster = DefendingTrainer.activeMonster
        val attack = attackingMonster.attacks[attackIndex - 1]

        if(attackingMonster != null || defendingMonster != null) {
            val calculator = AttackCalculator(
                attackingMonster = attackingMonster,
                defendingMonster = defendingMonster,
                attack = attack,
                battleStats = attackingMonster.battleStats
            )

            return
        }







    }

}
