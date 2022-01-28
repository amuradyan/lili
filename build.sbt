ThisBuild / scalaVersion := "3.1.0"
ThisBuild / organization := "liliville"

lazy val lili = (project in file("."))
   .settings(
     name := "Lili",
     libraryDependencies ++= Seq(
       "org.typelevel" %% "cats-effect" % "3.3.4",
       "org.http4s" %% "http4s-dsl" % "0.23.8",
       "org.http4s" %% "http4s-circe" % "0.23.8",
       "org.http4s" %% "http4s-server" % "0.23.8",
       "org.http4s" %% "http4s-blaze-server" % "0.23.8",
       "io.circe" %% "circe-generic" % "0.14.1",
       "org.scalatest" %% "scalatest" % "3.2.11" % Test
     ),
     Test / parallelExecution := false
   )
