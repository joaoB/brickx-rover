import java.io.{ByteArrayInputStream, OutputStream}

import models._
import org.scalatest.{FlatSpec, Matchers}

import scala.io.{Source, StdIn}

class AppSpec  extends FlatSpec with Matchers {

  it should "give a correct final position string" in {

    val rover1 = Rover(Position(1, 2), East)
    val rover2 = Rover(Position(3, 4), North)

    val list = MyApp.finalPosition(rover1 :: rover2 :: Nil)
    list.head shouldBe "1 2 E"
    list.last shouldBe "3 4 N"
  }

  it should "work correctly when output is from console - example given" in {

   val test = """|5 5
      |1 2 N
      |LMLMLMLMM
      |3 3 E
      |MMRMMRMRRM
      |
      |""".stripMargin

    val in = new ByteArrayInputStream(test.getBytes)

    Console.withIn(in)  {
      val result = MyApp.run(None).unsafePerformIO()
      result.head shouldBe "1 3 N"
      result(1) shouldBe "5 1 E"
      result(2) shouldBe "=========="
    }
  }

  it should "work correctly when output is from file - example given" in {
    val result = MyApp.run(Some("resources/examples")).unsafePerformIO()
    result.head shouldBe "1 3 N"
    result(1) shouldBe "5 1 E"
    result(2) shouldBe "=========="
  }

  it should "print to console the correct output" in {
    val stream = new java.io.ByteArrayOutputStream()
    Console.withOut(stream)  { MyApp.main(Array("resources/examples")) }
    val result = stream.toString.split("\n").toList
    result.head shouldBe "1 3 N"
    result(1) shouldBe "5 1 E"
    result(2) shouldBe "=========="
  }


}