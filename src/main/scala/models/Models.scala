package models

sealed abstract class SingleLetterInput(val id: String)

//Direction AST
sealed abstract class Direction(override val id: String) extends SingleLetterInput(id)
case object North extends Direction("N")
case object South extends Direction("S")
case object East extends Direction("E")
case object West extends Direction("W")

//The types of inputs our program supports
sealed abstract class Commands(override val id: String) extends SingleLetterInput(id)
case object M extends Commands("M")

sealed abstract class Rotation(override val id: String) extends Commands(id)
case object L extends Rotation("L")
case object R extends Rotation("R")

//Model
case class Position(x: Int, y: Int)
case class Inputs(rover: Rover, moves: List[Commands])
case class Rover(position: Position, direction: Direction)
case class Setup(plateau: Position, inputs: List[Inputs])