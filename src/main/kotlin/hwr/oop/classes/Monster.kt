package hwr.oop.classes

import kotlinx.serialization.*
import kotlinx.serialization.json.*

@Serializable
class Monster(val name: String, val type: Type, val stats: Stats, val attacks: List<Attack>) {
    //TODO: fun createMonster()
    // => User erstellt Monster und legt jedes Attribut fest, u.a. die Attacken
    // => Dafür wird ihm die Json ausgelesen und er darf daraus 3 Attacken pro Monster auswählen
    // => diese werden in einer separaten Mini-Liste als Attribut für dieses Monster gespeichert

}
