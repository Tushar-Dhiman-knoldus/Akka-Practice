import akka.actor.{Actor, ActorSystem, Props, PoisonPill}

class PoisonPill extends Actor {
  def receive: Receive = {
    case "start" =>
      println("Actor started")
    case "process" =>
      println("Processing message")
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
    system.terminate()
}
