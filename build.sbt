organization := "com.devialab"

name := "corbel-rem-utils"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies in ThisBuild ++= Seq(
  "io.corbel" % "rem-api" % "1.21.0-SNAPSHOT" % "provided",
  "io.corbel.lib" % "config" % "0.3.0-SNAPSHOT" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

resolvers in ThisBuild ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)