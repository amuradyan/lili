ThisBuild / scalaVersion := "3.1.0"
ThisBuild / organization := "liliville"

lazy val lili = (project in file("."))
   .settings(
     name := "Lili",
     libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test,
     Test / parallelExecution := false
   )
