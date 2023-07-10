import akka.actor.{Actor, ActorSystem, Props, Stash}

class StashThis extends Actor with Stash {
  def receive: Receive = {
    case "init" =>
      println("Initializing...")
      unstashAll()
      context.become(ready)
    case other =>
      println(s"Stashing message: $other")
      stash()
  }

  def ready: Receive = {
    case "process" =>
      println("Processing message")
    case "reset" =>
      println("Resetting...")
      unstashAll()
      context.unbecome()
    case other =>
      println(s"Ignoring message: $other")
  }
}

object StashExample extends App{
    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props[StashThis], "StashThis")

    actor ! "process" // Will be stashed
    actor ! "init"    // Initializes the actor and unstashes the message
    actor ! "process" // Processed by the ready behavior
    actor ! "reset"   // Resets the actor and unstashes the previously stashed message
    Thread.sleep(1000)
    system.terminate()
}
