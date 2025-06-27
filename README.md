# HWR OOP Lecture Project Template

[TODO]: # (Change README.md Headline to better fit to your project!)

This repository contains a student project created for an ongoing lecture on object-oriented
programming with Kotlin at HWR Berlin (summer term 2025).

> :warning: This code is for educational purposes only. Do not rely on it!

## Prerequisites

Installed:

1. IDE of your choice (e.g. IntelliJ IDEA)
2. JDK of choice installed (e.g. through IntelliJ IDEA)
3. Maven installed (e.g. through IntelliJ IDEA)
4. Git installed

## Local Development

This project uses [Apache Maven][maven] as build tool.

To build from your shell (without an additional local installation of Maven), ensure that `./mvnw`
is executable:

```
chmod +x ./mvnw
```

I recommend not to dive into details about Maven at the beginning.
Instead, you can use [just][just] to build the project.
It reads the repositories `justfile` which maps simplified commands to corresponding sensible Maven
calls.

With _just_ installed, you can simply run this command to perform a build of this project and run
all of its tests:

```
just build
```

## Abstract

We created a Pokémon-inspired monster battle simulator as our OOP lecture project.
It is a turn-based game in which two trainers face each other with a roster of monsters. 
Each monster has its own type, base stats, and available attacks.

The focus was to design the battle logic with reusable, testable Kotlin code.

## Most important features
- Turn-based battle system between two trainers
- Monsters with base stats (HP, damage, accuracy, type)
- Monsters can perform a set of attacks
- Command-line interface with commands to create trainers, battles, choose attacks, and more
- Test-driven development with Kotest

## Interesting problems we encountered during development
- Designing the battle logic while respecting OOP principles
- Structuring code for extensibility
- Implementing a flexible command-based CLI

## Feature List

| Number | Feature                                                                | Tests                               |
|--------|------------------------------------------------------------------------|-------------------------------------|
| 1      | Create a trainer                                                       | CreateTrainerCommandTest            |
| 2      | Create a battle with trainers and choose which Damage Formula to use   | CreateBattleCommandTest             |
| 3      | Choose one of four actions when it's your turn                         | ChooseActionCommandTest             |
| 4      | Choose one of your active monsters' Attacks                            | ChooseAttackCommandTest             |
| 5      | Heal your active monster                                               | healActiveMonster() in BattleTest   |
| 6      | Switch your active monster                                             | switchActiveMonster() in BattleTest |
| 7      | Surrender                                                              | surrender() in BattleTest           |
| 8      | Simple and complex damage formula                                      | CalculatorTest                      |
| 9      | Handling Type effectiveness between monsters                           | CalculatorTest and TypeTest         |
| 10     | Calculating if an attack will hit with its accuracy and randomization  | RandomizerTest                      |
| 11     | Calculating Crit chance with randomization                             | RandomizerTest                      |
| 12     | Different Attack Types triggering buffs and debuffs that are stackable | AttackTest                          |

## Commands

| Command                                                                                     | Description                                                                              |
|---------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `battle new --trainers=Ash,Misty,0`                                                         | Creates a new battle with the trainers Ash and Misty using the complex DamageCalculation |
| `battle new --trainers=Ash,Misty,1`                                                         | Creates a new battle with the trainers Ash and Misty using the simple DamageCalculation  |
| `battle attack --trainer=Ash, --attacker=Balbasaur, --attack=Razor Leaf, --target=Squirtle` | Attacks the opponents' Squirtle with the Razor Leaf attack of Ash's Bulbasaur            |

[maven]: https://maven.apache.org/
[just]: https://github.com/casey/just