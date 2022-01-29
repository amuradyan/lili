ThisBuild / scalaVersion := "3.1.0"
ThisBuild / organization := "liliville"
ThisBuild / version := "0.2.0"

val http4sVersion = "0.23.8"

lazy val lili = (project in file("."))
   .settings(
     name := "lili",
     libraryDependencies ++= Seq(
       "org.typelevel" %% "cats-effect" % "3.3.4",
       "org.http4s" %% "http4s-dsl" % http4sVersion,
       "org.http4s" %% "http4s-circe" % http4sVersion,
       "org.http4s" %% "http4s-server" % http4sVersion,
       "org.http4s" %% "http4s-blaze-server" % http4sVersion,
       "org.http4s" %% "http4s-blaze-client" % http4sVersion,
       "io.circe" %% "circe-generic" % "0.14.1",
       "com.47deg" %% "github4s" % "0.30.0",
       "org.scalatest" %% "scalatest" % "3.2.11" % Test
     ),
     Test / parallelExecution := false
   )
