package hwr.oop

interface CliCommand {
  fun matches(list: List<String>): Boolean
  fun handle(list: List<String>)
}