package kote

import akka.actor.FSM

/** Kote companion object */
object Kote {

  sealed trait State

  sealed trait Data

  object State {

    case object Sleeping extends State

    case object Awake extends State

  }

  object Data {


    case class VitalSigns(hunger: Int) extends Data
  }

  object Commands {

    case object WakeUp

    case object Stroke

  }

}

/** Kote Tamakotchi mimimi njawka! */
class Kote extends FSM[Kote.State, Kote.Data] {

  import Kote._

  startWith(State.Sleeping, Data.VitalSigns(hunger = 60))

  when(State.Sleeping) {
    //    FSM.NullFunction
    case Event(Commands.WakeUp, _) =>
      goto(State.Awake)
  }

  when(State.Awake) {
    //      sender() ! "purrr"
    //      stay()
    case Event(Commands.Stroke, Data.VitalSigns(hunger)) if hunger < 30 =>
      stay() replying "purrr"
    case Event(Commands.Stroke, Data.VitalSigns(hunger)) =>
      stay() replying "miaw!!11"
  }
}
