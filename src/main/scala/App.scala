import models._
import reader.Parser
import scalaz.effect._
import services.{FoldLeftService, MoveRoverService}

import scala.io.Source

//TODO: move this to another place
case class Runner(service : MoveRoverService, setup: Setup) {
  def run : List[Rover] = service.moveIt(setup)
}

object MyApp extends App{

  override def main(args: Array[String]): Unit = run(args.headOption).unsafePerformIO map println

  def run(from: Option[String]) = {
    def output(msg: String): IO[String] = IO { msg }
    val ioLines = from.map(fromFile) getOrElse fromConsole
    val service = FoldLeftService //TODO: make this dynamic

    for {
      allLines <- ioLines
      textLines = allLines mkString "\n"
      setup = Parser.parse(textLines)
      runner = Runner(service,  setup)
      finalRovers = runner.run
      finalPos = finalPosition(finalRovers)
      _ <- output(finalPos.mkString("\n"))
      _ <- output("==========")
    } yield finalPos :+ "=========="
  }


  def finalPosition(rovers : List[Rover]) : List[String] = rovers.map { rover =>
    s"${rover.position.x} ${rover.position.y} ${rover.direction.id}"
  }

  private def fromFile(filename: String) : IO[List[String]] = {
    for {
      allLines <- IO { Source.fromFile(filename).getLines().toList }
    } yield allLines
  }


  private def fromConsole = {
    def read: IO[String] = IO { scala.io.StdIn.readLine }

    def loopReading(acc : List[String] = Nil) : IO[List[String]] = (for {
      line <- read
    } yield if (line.isEmpty) IO { acc } else loopReading(acc :+ line)) flatMap identity

     for {
      plateau <- read
      inputs <- loopReading()
    } yield plateau :: inputs

  }


}
