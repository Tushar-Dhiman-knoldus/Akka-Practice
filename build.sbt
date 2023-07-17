ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "Actor-Practice"
  )
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.8.0",
  "com.typesafe.akka" %% "akka-remote" % "2.8.0",
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "io.aeron" % "aeron-driver" % "1.40.0",
  "io.aeron" % "aeron-client" % "1.40.0",
  "com.typesafe.akka" %% "akka-cluster" % "2.8.0",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.8.0",
  "com.typesafe.akka" %% "akka-testkit" % "2.8.0" % Test,
  "org.apache.logging.log4j" % "log4j-api" % "2.20.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.20.0",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.20.0"
)