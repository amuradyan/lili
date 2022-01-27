ThisBuild / scalaVersion := "3.1.0"
ThisBuild / organization := "lili.amuradyan"

lazy val lili = (project in file("."))
  .settings(
    name := "Lili",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.11" % Test,
    parallelExecution in Test := false
  )