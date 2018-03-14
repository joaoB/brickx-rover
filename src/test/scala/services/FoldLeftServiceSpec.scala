package services

import models._
import org.scalatest.{FlatSpec, Matchers}

class FoldLeftServiceSpec extends FlatSpec with Matchers {

    it should "return the correct output for the example scenario" in {
      val plateau = Position(5,5)

      val rover1 = Rover(Position(1,2), North)
      val commands1 = List(L,M,L,M,L,M,L,M,M)
      val input1 = Inputs(rover1, commands1)

      val rover2 = Rover(Position(3,3), East)
      val commands2 = List(M,M,R,M,M,R,M,R,R,M)
      val input2 = Inputs(rover2, commands2)

      val result = FoldLeftService.moveIt(Setup(plateau, List(input1, input2)))

      result.size shouldBe 2

      result.head.position.x shouldBe 1
      result.head.position.y shouldBe 3
      result.head.direction shouldBe North

      result.last.position.x shouldBe 5
      result.last.position.y shouldBe 1
      result.last.direction shouldBe East
    }

}

class AgainstTheOtherSpec extends FlatSpec with Matchers {

  it should "return the same value for both services" in {
    val plateau = Position(50, 50)

    val rover = Rover(Position(1,2), North)
    val list = List(L,M,R)

    val commands = for (_ <- (0 until 200).toList) yield scala.util.Random.shuffle(list).head
    val input = Inputs(rover, commands)

    val s = Setup(plateau, List(input))

    val fold = FoldLeftService.moveIt(s)
    val state = StateService.moveIt(s)

    fold shouldBe state

  }

}