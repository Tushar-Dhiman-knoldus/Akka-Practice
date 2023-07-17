import akka.actor.{Actor, ActorSystem, PoisonPill, Props}
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class PoisonPill extends Actor {

  val log: Logger = LogManager.getLogger(getClass)
  def receive: Receive = {
    case "start" =>
      log.info("Actor started")
    case "process" =>
      log.info("Processing message")
    case "stop" =>
      self ! PoisonPill

  }
}

object PoisonPillExample extends App {
    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props[PoisonPill], "PoisonPill")

    actor ! "start"
    actor ! "process"
    actor ! "stop"
    Thread.sleep(1000) // Allow time for actor termination
    actor ! "process"
    actor ! "process"
//    system.terminate()
}
