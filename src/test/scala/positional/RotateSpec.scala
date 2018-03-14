package positional

import models._
import org.scalatest._

class RotateSpec extends FlatSpec with Matchers {

  private def rotate(from : Direction, times : Int) =
    List.fill(times)(R).foldLeft(from : Direction) { (direction, towards) => Rotate(towards, direction) }

  private def backAndForward(from : Direction) = {
    val f = Rotate(L, from)
    Rotate(R, f)
  }

  it should "rotate correctly from North" in {
    Rotate(L, North) shouldBe West
    Rotate(R, North) shouldBe East

    rotate(North, 2) shouldBe South
    rotate(North, 4) shouldBe North

    backAndForward(North) shouldBe North
  }


  it should "rotate correctly from South" in {
    Rotate(L, South) shouldBe East
    Rotate(R, South) shouldBe West

    rotate(South, 2) shouldBe North
    rotate(South, 4) shouldBe South

    backAndForward(South) shouldBe South
  }

  it should "rotate correctly from West" in {
    Rotate(L, West) shouldBe South
    Rotate(R, West) shouldBe North

    rotate(West, 2) shouldBe East
    rotate(West, 4) shouldBe West

    backAndForward(West) shouldBe West
  }

  it should "rotate correctly from East" in {
    Rotate(L, East) shouldBe North
    Rotate(R, East) shouldBe South

    rotate(East, 2) shouldBe West
    rotate(East, 4) shouldBe East

    backAndForward(East) shouldBe East
  }
}