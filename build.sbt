ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "Lem"
  )

libraryDependencies += "com.lihaoyi" %% "fastparse" % "3.1.0"
libraryDependencies += "com.lihaoyi" %% "pprint" % "0.9.0"
libraryDependencies += "org.typelevel" %% "cats-core" % "2.10.0"
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.5.4"
