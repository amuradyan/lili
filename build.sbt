ThisBuild / scalaVersion := "3.1.0"
ThisBuild / organization := "liliville"

val http4sVersion = "0.23.8"

lazy val lili = (project in file("."))
   .settings(
     name := "Lili",
     libraryDependencies ++= Seq(
       "org.typelevel" %% "cats-effect" % "3.3.4",
       "org.http4s" %% "http4s-dsl" % http4sVersion,
       "org.http4s" %% "http4s-circe" % http4sVersion,
       "org.http4s" %% "http4s-server" % http4sVersion,
       "org.http4s" %% "http4s-blaze-server" % http4sVersion,
       "org.http4s" %% "http4s-blaze-client" % http4sVersion,
       "io.circe" %% "circe-generic" % "0.14.1",
       "org.scalatest" %% "scalatest" % "3.2.11" % Test
     ),
     Test / parallelExecution := false
   )
