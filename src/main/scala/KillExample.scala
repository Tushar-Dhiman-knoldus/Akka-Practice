import akka.actor.{Actor, ActorSystem, Kill, Props}

class Kill extends Actor {
  def receive: Receive = {
    case "start" =>
      println("Actor started")
    case "process" =>
      println("Processing message")
    case "stop" =>
      self ! Kill
  }
}

object KillExample extends App{

    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props[Kill], "Kill")

    actor ! "start"
    actor ! "process"
    actor ! "stop"

    Thread.sleep(1000)
    system.terminate()
}
