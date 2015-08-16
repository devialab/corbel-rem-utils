organization := "com.devialab"

name := "corbel-rem-utils"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

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

// Publishing settings
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo := Some("Artifactory Realm" at "http://artifacts.devialab.com/artifactory/devialab-snapshot-local;build.timestamp=" + new java.util.Date().getTime)

publishMavenStyle := true