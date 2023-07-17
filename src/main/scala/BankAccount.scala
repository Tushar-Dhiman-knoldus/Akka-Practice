import BankAccountQuestion.BankAccount.{Deposit, Statement, Withdraw}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object BankAccountQuestion extends App {

  object BankAccount {
    case class Deposit(amount: Int)

    case class Withdraw(amount: Int)

    case object Statement

    case class TransactionSuccess(message: String)

    case class TransactionFailure(message: String)
  }

  class BankAccount extends Actor {

    import BankAccount._

    var funds = 0

    override def receive: Receive = {

      case Deposit(amount) =>
        if (amount < 0) sender() ! TransactionFailure("invalid deposit amount")
        else {
          funds += amount
          sender() ! TransactionSuccess(s"successfully deposited $amount")
        }
      case Withdraw(amount) =>
        if (amount < 0) sender() ! TransactionFailure("invalid withdraw amount")
        else if (amount > funds) sender() ! TransactionFailure("insufficient funds")
        else {
          funds -= amount
          sender() ! TransactionSuccess(s"successfully withdrew $amount")
        }
      case Statement => sender() ! s"Your balance is $funds"
    }
  }

  object Person {
    case class LiveTheLife(account: ActorRef)
  }

  class Person extends Actor {

    import Person._

    override def receive: Receive = {
      case LiveTheLife(account) =>
        account ! Deposit(10000)
        account ! Withdraw(90000)
        account ! Withdraw(500)
        account ! Statement
      case message => println(message.toString)

    }
  }
  import Person._
  val system = ActorSystem("BankAccount")
  val account = system.actorOf(Props[BankAccount], "bankAccount")
  val person = system.actorOf(Props[Person],"billionaire")

  person ! LiveTheLife(account)
}
