package positional

import models._

object Rotate {
  private val coordinates = List(North, East, South, West)
  private val size = coordinates.size

  private def getNextCoordinate(pivot : Direction, list : List[Direction]) = {
    val newDirectionIndex = (list.indexOf(pivot) + 1) % size
    list(newDirectionIndex)
  }

  def apply(rotateTo : Rotation, from : Direction) : Direction = rotateTo match {
    case R => getNextCoordinate(from, coordinates)
    case L => getNextCoordinate(from, coordinates.reverse)
  }
}

object Move {

  def apply(direction: Direction, position: Position, plateau: Position): Position = {
    val newPos = move(direction, position)
    adjustPosition(plateau, newPos)
  }

  def move(direction: Direction, position: Position) : Position = direction match {
    case North => position.copy(y = position.y + 1)
    case South => position.copy(y = position.y - 1)
    case East => position.copy(x = position.x + 1)
    case West => position.copy(x = position.x - 1)
  }

  def adjustPosition(plateau: Position, current: Position) : Position = {
    val adjustedMinX = current.x max 0
    val adjustedMinY = current.y max 0
    val adjustedX = adjustedMinX min plateau.x
    val adjustMinY = adjustedMinY min plateau.y

    current.copy(x = adjustedX, y = adjustMinY)
  }
}