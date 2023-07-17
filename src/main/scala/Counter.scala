import akka.actor.{Actor, ActorSystem, Props}

object CounterApp extends App {

  case object Increment

  case object Decrement

  case object Print

  class Counter extends Actor {
    var count = 0

    override def receive: Receive = {
      case Increment =>
        count += 1
      case Decrement =>
        count -= 1
      case Print => println(s"Count :$count")
    }
  }
  val system = ActorSystem("CounterActorSystem")
  val counterActor = system.actorOf(Props[Counter], "Counter")
  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Increment
  counterActor ! Decrement
  counterActor ! Decrement
  counterActor ! Print
}

