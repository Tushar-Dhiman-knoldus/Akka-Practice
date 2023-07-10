import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class AskPattern extends Actor {
  def receive: Receive = {
    case "question" =>
      val answer = "This is the answer"
      sender() ! answer
  }
}

object AskPatternExample extends App{

    val system = ActorSystem("MySystem")
    val actor = system.actorOf(Props[AskPattern], "AskPattern")

    implicit val timeout: Timeout = Timeout(3.seconds)

    val question = "What is the answer?"
    val future: Future[Any] = actor ? "question"
    val answer: Any = Await.result(future, timeout.duration)

    println(s"Question: $question")
    println(s"Answer: $answer")

    system.terminate()

}
