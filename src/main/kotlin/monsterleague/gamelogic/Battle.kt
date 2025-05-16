package monsterleague.gamelogic

class Battle(
    val battleID: Int,
    var round: Int,
    var winner: String?,
    val trainers: List<Trainer>
) {

fun proofIfBattleIsFinished():Boolean{
    return trainers[0].monsters.isEmpty() || trainers[1].monsters.isEmpty()
    //determineWinner()
    //saveBattleInformation()
}

    //TODO: Gewinner festlegen
    fun determineWinner(){

    }

    //TODO: Kampf Informationen speichern
    fun saveBattleInformation(){

    }

}
