import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorMessageBehaviour extends App {
  private class ActorCapabilities extends Actor {

    override def receive: Receive = {
      case "Hi!" => sender() ! "Hello, there!"
      case message: String => println(s"{${self}} I have received $message")
      case number: Int => println(s"[ActorCapabilities] I have received a number $number")
      case SpecialMessage(contents) => println(s"[ActorCapabilities] I have received something special: $contents")
      case SendMessageToYourself(content) => self ! content
      case SayHiTo(ref) => ref ! "Hi!"
      case WirelessPhoneMessage(content, ref) => ref forward (content + "s")
     }

  }

  val system: ActorSystem = ActorSystem("ActorMessageBehaviour")
  val actor: ActorRef = system.actorOf(Props[ActorCapabilities], "ActorCapabilities")

  actor ! "hello, actor"
  actor ! 42

  case class SpecialMessage(counter: String)
  actor ! SpecialMessage("some special content")

  case class SendMessageToYourself(content: String)
  actor ! SendMessageToYourself("I am an actor and I am proud of it")

  val alice = system.actorOf(Props[ActorCapabilities], "alice")
  val bob = system.actorOf(Props[ActorCapabilities], "bob")

  case class SayHiTo(ref: ActorRef)
  alice ! SayHiTo(bob)

  alice ! "Hi"

  private case class WirelessPhoneMessage(content: String, ref: ActorRef)
  alice ! WirelessPhoneMessage("Hi", bob)

}