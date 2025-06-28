package hwr.oop.monsterleague.gamelogic.cli

interface CliCommand {
  fun matches(list: List<String>): Boolean
  fun handle(list: List<String>)
}