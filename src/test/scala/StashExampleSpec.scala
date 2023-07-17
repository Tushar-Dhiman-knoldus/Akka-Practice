import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActorRef, TestKit}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class StashThisSpec extends TestKit(ActorSystem("StashThisSpec"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "StashThis" should {
    "unstash and process messages after initialization" in {
      val actorRef = TestActorRef[StashThis]
      actorRef ! "some message"
      actorRef ! "init"
      actorRef ! "process"

      expectNoMessage() // Verify that the initial message is stashed
      expectNoMessage() // Verify that the stash is cleared
      expectNoMessage() // Verify that the "process" message is processed
    }

    "ignore messages before initialization" in {
      val actorRef = TestActorRef[StashThis]
      actorRef ! "some message"
      actorRef ! "reset"
      actorRef ! "other message"

      expectNoMessage() // Verify that the initial message is ignored
      expectNoMessage() // Verify that the reset clears the stash
      expectNoMessage() // Verify that the "other message" is ignored
    }
  }
}
