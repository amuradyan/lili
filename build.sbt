ThisBuild / scalaVersion := "3.1.0"
ThisBuild / organization := "liliville"

lazy val lili = (project in file("."))
   .settings(
     name := "Lili",
     libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test,
     libraryDependencies += "org.http4s" %% "http4s-dsl" % "0.23.8",
     libraryDependencies += "org.typelevel" %% "cats-effect" % "3.3.4",
     libraryDependencies += "org.http4s" %% "http4s-server" % "0.23.8",
     libraryDependencies += "org.http4s" %% "http4s-blaze-server" % "0.23.8",
     Test / parallelExecution := false
   )
