package reader
import models._

import scala.util.parsing.combinator._

//TODO: do not allow negative positions

object Parser extends JavaTokenParsers {

  def parse(line: String) : Setup = parseAll(marsRovers, line) match {
    case Success(r, _) => r
    //case _             => TODO: fix this
  }

  // grammar
  def marsRovers: Parser[Setup] = setup

  def position : Parser[Position] = wholeNumber ~ wholeNumber ^^ { case x ~ y => Position(x.toInt, y.toInt) }

  def rover : Parser[Rover] = position ~ direction ^^ { case p ~ d => Rover(p, d) }

  def direction : Parser[Direction] = north | south | east | west

  def commands : Parser[List[Commands]] = rep1(left | right | move)

  def input : Parser[models.Inputs] = rover ~ commands ^^ { case r ~ c => Inputs(r, c)}

  def setup : Parser[Setup] = position ~ rep(input) ^^ { case p ~ i => Setup(p, i) }

  private def singleLetterInput[T <: SingleLetterInput](dir: T) : Parser[T] = dir.id.r ^^ { _ => dir}

  private def north : Parser[Direction] = singleLetterInput(North)
  private def south : Parser[Direction] = singleLetterInput(South)
  private def east : Parser[Direction] = singleLetterInput(East)
  private def west : Parser[Direction] = singleLetterInput(West)

  private def left : Parser[Commands] = singleLetterInput(L)
  private def right : Parser[Commands] = singleLetterInput(R)
  private def move : Parser[Commands] = singleLetterInput(M)

}
