ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.11"

lazy val root = (project in file("."))
  .settings(
    name := "Actor-Practice"
  )
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.8.0",
  "com.typesafe.akka" %% "akka-remote" % "2.8.0",
  "io.aeron" % "aeron-driver" % "1.40.0",
  "io.aeron" % "aeron-client" % "1.40.0",
  "com.typesafe.akka" %% "akka-cluster" % "2.8.0",
  "com.typesafe.akka" %% "akka-cluster-tools" % "2.8.0"
)