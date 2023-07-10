
import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

class WorkerActor extends Actor {
  def receive: Receive = {
    case "process" =>
      val result = performSomeAsyncTask()
      result.pipeTo(sender())
  }

  private def performSomeAsyncTask(): Future[String] = {
    // Simulating an asynchronous operation
    import context.dispatcher
    val result = Future {
      Thread.sleep(1000)
      "Task completed"
    }
    result
  }
}

class ResultPrinterActor extends Actor {
  def receive: Receive = {
    case result: String =>
      println(s"Received result: $result")
  }
}

object PipeToExample extends App {
    val system = ActorSystem("MySystem")
    val worker = system.actorOf(Props[WorkerActor], "workerActor")
    val printer = system.actorOf(Props[ResultPrinterActor], "resultPrinterActor")

    import system.dispatcher
    implicit val timeout: Timeout = Timeout(3.seconds)

    val resultFuture = (worker ? "process").mapTo[String]
    resultFuture.pipeTo(printer)

}
