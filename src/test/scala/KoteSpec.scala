import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestFSMRef, TestKit}
import kote.Kote
import org.scalatest.BeforeAndAfterAll
import org.scalatest.freespec.AnyFreeSpecLike
import org.scalatest.matchers.should.Matchers

import scala.concurrent.Await
import scala.concurrent.duration._

class KoteSpec(_system: ActorSystem) extends TestKit(_system) with ImplicitSender with Matchers with AnyFreeSpecLike with BeforeAndAfterAll {

  import Kote._

  def this() = this(ActorSystem("KoteSpec"))

  override def afterAll(): Unit = {
    system.terminate()
    Await.result(system.whenTerminated, 10.seconds)
  }

  class TestedKote {
    val kote: TestFSMRef[State, Data, Kote] = TestFSMRef(new Kote)
  }

  "A Kote actor" - {
    "should sleep at birth" in new TestedKote {
      kote.stateName should be(State.Sleeping)
      kote.stateData should be(Data.VitalSigns(hunger = 60))
    }

    "should wake up on command" in new TestedKote {
      kote ! Commands.WakeUp
      kote.stateName should be(State.Awake)
    }

    "while in Awake state" - {
      trait AwakeKoteState extends TestedKote {
        def initialHunger: Int

        kote.setState(State.Awake, Data.VitalSigns(initialHunger))
      }

      trait FullUp {
        def initialHunger: Int = 15
      }

      trait Hungry {
        def initialHunger: Int = 75
      }

      "should purr on stroke if not hungry" in new AwakeKoteState with FullUp {
        kote ! Commands.Stroke
        expectMsg("purrr")
        kote.stateName should be(State.Awake)
      }

      "should miaw on stroke if hungry" in new AwakeKoteState with Hungry {
        kote ! Commands.Stroke
        expectMsg("miaw!!11")
        kote.stateName should be(State.Awake)
      }
    }
  }

}
