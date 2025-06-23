package hwr.oop

import hwr.oop.projects.kingdom_2025.persistence.FileSystemPersistenceAdapter

fun main(args: Array<String>) {
  val fileSystemPersistenceAdapter = FileSystemPersistenceAdapter()
  val cli = Cli(
    loadGamePort = fileSystemPersistenceAdapter,
    saveGamePort = fileSystemPersistenceAdapter,
  )
  cli.handle(args.toList())
}

// TODO kingdom game new --players=Alice,Bob -> stdout: ID 1234
// TODO kingdom game --id=1234