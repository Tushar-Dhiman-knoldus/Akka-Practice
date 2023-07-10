import akka.actor.{Actor, ActorLogging, ActorSystem, Props}

object Akka extends App {

  case object FOOD

  case object PLAY

  case object SlEEP

  class Parent extends Actor {
    val childActor = context.actorOf(Props[Child])

    override def receive: Receive = {

      case FOOD => childActor ! FOOD
      case PLAY => childActor ! PLAY
      case SlEEP => childActor ! SlEEP

    }
  }

  class Child extends Actor with ActorLogging {
    override def receive: Receive = {
      case FOOD => log.info(s"${context.sender()} is eating FOOD")
      case PLAY => log.info(s"$self is Playing")
      case SlEEP => log.info(s"$self is Sleeping")
    }
  }

  val system = ActorSystem("ActorSystem")
  val parentActor = system.actorOf(Props[Parent])
  parentActor ! FOOD
  parentActor ! SlEEP
  parentActor ! PLAY
}
