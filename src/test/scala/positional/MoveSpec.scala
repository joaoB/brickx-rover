package positional

import models._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.prop.TableDrivenPropertyChecks._

class MoveSpec extends FlatSpec with Matchers {

  private val basePos = Position(0, 0)
  private val plateau = Position(5, 5)

  it should "move towards North" in {
    Move.move(North, basePos) shouldBe Position(0, 1)
  }

  it should "move towards South" in {
    Move.move(South, basePos) shouldBe Position(0, -1)
  }

  it should "move towards East" in {
    Move.move(East, basePos) shouldBe Position(1, 0)
  }

  it should "move towards West" in {
    Move.move(West, basePos) shouldBe Position(-1, 0)
  }


  it should "adjust position for a given plateau" in {
    val testCases =
      Table(
        ("plateau", "wrong position", "expected"),
        (plateau, Position(-1, 0), Position(0, 0)),
        (plateau, Position(0, -1), Position(0, 0)),
        (plateau, Position(0, 0), Position(0, 0)),
        (plateau, Position(-1, -1), Position(0, 0)),
        (plateau, Position(6, 0), Position(5, 0)),
        (plateau, Position(0, 6), Position(0, 5)),
        (plateau, Position(6, 6), Position(5, 5)),
        (plateau, Position(5, 5), Position(5, 5)),
        (plateau, Position(4, 5), Position(4, 5)),
        (plateau, Position(4, 4), Position(4, 4)),
        (Position(0, 0), Position(4, 4), Position(0, 0)),
        (Position(0, 0), Position(-1, 4), Position(0, 0)),
        (Position(0, 0), Position(-1, -2), Position(0, 0)),
        (Position(0, 0), Position(0, 0), Position(0, 0))
      )

    forAll(testCases) { case (p, wrong, expected) =>
        Move.adjustPosition(p, wrong) shouldBe expected
    }
  }


  it should "move correctly considering the plateau" in {
    val testCases =
      Table(
        ("plateau", "direction", "from", "expected"),
        (plateau, North, Position(0, 0), Position(0, 1)),
        (plateau, South, Position(0, 0), Position(0, 0)),
        (plateau, West, Position(0, 0), Position(0, 0)),
        (plateau, East, Position(0, 0), Position(1, 0)),
        (plateau, West, Position(5, 5), Position(4, 5)),
        (plateau, East, Position(5, 5), Position(5, 5)),
        (plateau, East, Position(0, 0), Position(1, 0))
      )

    forAll(testCases) { case (p, from, towards, expected) =>
      Move(from, towards, p) shouldBe expected
    }
  }

}