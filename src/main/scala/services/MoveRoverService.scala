package services
import models._
import positional.{Move, Rotate}
import scalaz.Scalaz._
import scalaz._

trait MoveRoverService {

   protected def applyInput(plateau: Position, c: Commands): (Rover) ⇒ Rover =
    (r: Rover) => (c, r) match {
      case (L, Rover(pos, dir)) => Rover(pos, Rotate(L, dir))
      case (R, Rover(pos, dir)) => Rover(pos, Rotate(R, dir))
      case (M, Rover(pos, dir)) => Rover(Move(dir, pos, plateau), dir)
    }

  def moveIt(setup : Setup) : List[Rover]
}

object StateService extends MoveRoverService {

  //TODO: make this tailrec or trampoline to avoid SO
  def simulateMachine(plateau: Position, commands: List[Commands]): State[Rover, Unit] = {
    val stateList = commands.map { c => modify(applyInput(plateau, c)) }
    val state = StateT.stateMonad[Rover]
    for {
      _ <- state.sequence(stateList)
    } yield ()
  }

  def moveIt(setup: Setup) : List[Rover] = setup.inputs map { input =>
      simulateMachine(setup.plateau, input.moves).exec(input.rover)
    }
}

object FoldLeftService extends MoveRoverService {
  def moveIt(setup: Setup) : List[Rover] = {
    val plateau = setup.plateau

    setup.inputs map {
      case Inputs(initialRover, moves) =>
        moves.foldLeft(initialRover) { (currentRover, nextCommand) => applyInput(plateau, nextCommand)(currentRover) }
    }
  }
}