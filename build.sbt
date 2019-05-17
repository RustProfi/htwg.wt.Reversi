enablePlugins(JavaAppPackaging, AshScriptPlugin)

name := "Reversi in Scala"
organization := "de.htwg.se"
version := "2.0"
scalaVersion := "2.12.7"

dockerBaseImage := "openjdk:8-jre-alpine"
packageName in Docker := "reversi"
dockerExposedPorts := Seq(8080)


libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.12" % "2.0.3"

libraryDependencies += "com.google.inject" % "guice" % "4.1.0"

libraryDependencies += "net.codingwell" %% "scala-guice" % "4.1.0"

libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.12" % "1.0.6"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.8"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.19"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.8"

coverageExcludedPackages := "de.htwg.se.reversi.aview.gui;de.htwg.se.reversi.Reversi"