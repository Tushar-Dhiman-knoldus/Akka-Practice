import akka.actor.{Actor, ActorSystem, Props}

class BehaviourChange extends Actor {
  def receive: Receive = initialState

  def initialState: Receive = {
    case "start" =>
      println("Initial state: Start command received")
      context.become(processingState)
  }

  def processingState: Receive = {
    case "process" =>
      println("Processing state: Process command received")
      context.unbecome()
  }
}

object BehaviourChangeExample extends App{
    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props[BehaviourChange], "BehaviourChange")

    actor ! "start" // Switch to processing state
    actor ! "process" // Switch back to initial state
}
