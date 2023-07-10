package ActorRemoting

import akka.actor.{Actor, ActorIdentity, ActorLogging, ActorSystem, Identify, Props}
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps
import scala.util.{Failure, Success}

object RemoteActors extends App {

  val localSystem = ActorSystem("localSystem", ConfigFactory.load("ActorRemoting/remoteActors.conf"))
  val localSimpleActor = localSystem.actorOf(Props[SimpleActor], "simpleLocalActor")
  localSimpleActor ! "hello, local actor!"

  // send a message to the REMOTE simple actor
  // METHOD 1: actor selection
  val remoteActorSelection = localSystem.actorSelection("akka://remoteSystem@localhost:2552/user/remoteLocalActor")
  remoteActorSelection ! "hello from the \"local\" JVM"

  //METHOD 2: resolve the actor selection to an actor ref
  import localSystem.dispatcher
  implicit val timeout = Timeout(3 seconds)
  val remoteActorRefFuture = remoteActorSelection.resolveOne()
  remoteActorRefFuture.onComplete{
    case Success(actorRef) => actorRef ! "I've resolved you in a future"
    case Failure(ex) => println(s"I've failed to resolve the remote actor because $ex")
  }

  // METHOD 3: actor identification via messages
  /*
    - actor resolver will ask for an actor selection from the local actor system
    - actor resolver will send a Identify(42) to the actor selection
    - the remote actor will AUTOMATICALLY respond with ActorIdentity(42, actorRef)
    - the actor resolver is free to use the remote actorRef
   */
  class ActorResolver extends Actor with ActorLogging {
    override def preStart(): Unit = {
      val selection = context.actorSelection("akka://remoteSystem@localhost:2552/user/remoteLocalActor")
      selection ! Identify(42)
    }

    override def receive: Receive = {
      case ActorIdentity(42, Some(actorRef)) =>
        actorRef ! "Thank you for identifying yourself!"
    }
  }

  localSystem.actorOf(Props[ActorResolver],"localActorResolver")

}

object RemoteActors_Remote extends App {
  val remoteSystem = ActorSystem("remoteSystem", ConfigFactory.load("ActorRemoting/remoteActors.conf").getConfig("remoteSystem"))
  val remoteSimpleActor = remoteSystem.actorOf(Props[SimpleActor], "remoteLocalActor")
  remoteSimpleActor ! "hello, remote actor!"
}
