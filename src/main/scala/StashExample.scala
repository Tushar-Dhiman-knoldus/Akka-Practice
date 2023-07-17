import akka.actor.{Actor, ActorSystem, Props, Stash}
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
class StashThis extends Actor with Stash {

  val log: Logger = LogManager.getLogger(getClass)
  def receive: Receive = {
    case "init" =>
      log.info("Initializing...")
      unstashAll()
      context.become(ready)
    case other =>
      log.info(s"Stashing message: $other")
      stash()
  }

  def ready: Receive = {
    case "process" =>
      log.info("Processing message")
    case "reset" =>
      log.info("Resetting...")
      unstashAll()
      context.unbecome()
    case other =>
      log.info(s"Ignoring message: $other")
  }
}

object StashExample extends App{
    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props[StashThis], "StashThis")

    actor ! "process" // Will be stashed
    actor ! "init"    // Initializes the actor and unstashes the message
    actor ! "process" // Processed by the ready behavior
    actor ! "reset"   // Resets the actor and unstashes the previously stashed message
//    Thread.sleep(1000)
//    system.terminate()
}
