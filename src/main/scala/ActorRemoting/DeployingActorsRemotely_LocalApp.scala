package ActorRemoting

import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object DeployingActorsRemotely_LocalApp extends App{
   val system = ActorSystem("LocalActorSystem", ConfigFactory.load("ActorRemoting/deployingActorsRemotely.conf").getConfig("localApp"))
   val simpleActor = system.actorOf(Props[SimpleActor], "remoteActor")
  simpleActor ! "hello, remote actor!"
}

object DeployingActorsRemotely_RemoteApp extends App{
  val system = ActorSystem("RemoteActorSystem", ConfigFactory.load("ActorRemoting/deployingActorsRemotely.conf").getConfig("remoteApp"))
}