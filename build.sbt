name := "hello-akka-fsm"

version := "0.1"

scalaVersion := "2.13.3"

val AkkaVersion = "2.6.7"
val ScalaTestVersion = "3.2.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % AkkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % AkkaVersion % Test,
  "org.scalactic" %% "scalactic" % "3.2.0",
  "org.scalatest" %% "scalatest" % ScalaTestVersion % "test",
  "org.scalatest" %% "scalatest-freespec" % ScalaTestVersion % "test",
  "org.scalatest" %% "scalatest-mustmatchers" % ScalaTestVersion % "test",
  "org.scalatest" %% "scalatest-shouldmatchers" % ScalaTestVersion % "test",
)
