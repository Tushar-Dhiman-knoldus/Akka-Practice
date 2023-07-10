import akka.actor.{Actor, ActorSystem, Props}

class OriginalActor extends Actor {
  def receive: Receive = {
    case "start" =>
      println("OriginalActor started")
      val forwardActor = context.actorOf(Props[ForwardActor], "forwardActor")
      forwardActor.forward("process", context)
  }
}

class ForwardActor extends Actor {
  def receive: Receive = {
    case "process" =>
      println("ForwardActor processing message")
    // Process the message
  }
}

object ForwardExample extends App {
    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props[OriginalActor], "originalActor")

    actor ! "start"
  Thread.sleep(1000)
  system.terminate()

}
