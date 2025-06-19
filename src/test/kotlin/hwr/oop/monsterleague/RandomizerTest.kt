package hwr.oop.monsterleague

import hwr.oop.monsterleague.gamelogic.Randomizer
import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat

class RandomizerTest: AnnotationSpec() {

@Test
fun `random Numbers`(){
  var number = Randomizer().getRandomNumber()
  println(number)
}
}