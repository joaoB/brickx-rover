package reader

import models._
import org.scalatest._
import reader.Parser.parseAll

class ParserSpec extends FlatSpec with Matchers {

  private def aux[T](p : Parser.Parser[T], line: String) = parseAll(p, line).get

  it should "correctly parse a position" in {
    aux(Parser.position, "6 6") shouldBe Position(6, 6) //one white space
    aux(Parser.position, "6      1") shouldBe Position(6, 1) //multiple white spaces
  }

  it should "correctly parse direction" in {
    aux(Parser.direction, "N") shouldBe North
    aux(Parser.direction, "S") shouldBe South
    aux(Parser.direction, "E") shouldBe East
    aux(Parser.direction, "W") shouldBe West
  }

  it should "fail when direction is not known" in {
    an [RuntimeException] should be thrownBy aux(Parser.direction, "NE")
  }

  it should "correctly parse a rover" in {

    val rover = aux(Parser.rover, "1 2 N")

    rover.position.x shouldBe 1
    rover.position.y shouldBe 2
    rover.direction shouldBe North
  }

  it should "correctly parse the movement commands" in {
    aux(Parser.commands, "L") shouldBe L :: Nil
    aux(Parser.commands, "R") shouldBe R :: Nil
    aux(Parser.commands, "M") shouldBe M :: Nil

    aux(Parser.commands, "LRMLRM") shouldBe List(L, R, M, L, R, M)
  }

  it should "require at least on command" in {
    an [RuntimeException] should be thrownBy aux(Parser.commands, "")
  }

  it should "parse the inputs correct" in {
    val m = "MMRMMRMRRM"

    val inputText = s"""1 3 E
                  |$m
                  |""".stripMargin
    val input = aux(Parser.input, inputText)

    input.rover.direction shouldBe East
    input.rover.position shouldBe Position(1, 3)

    input.moves.map(_.toString).mkString shouldBe m
  }



  it should "parse a full example" in {
    val example = """
                 |5 5
                 |1 2 N
                 |LMLMLMLMM
                 |""".stripMargin

    val setup = aux(Parser.setup, example)
    setup.plateau.x shouldBe 5
    setup.plateau.y shouldBe 5
  }


}