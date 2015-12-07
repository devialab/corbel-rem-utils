organization := "com.devialab"

name := "corbel-rem-utils"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.11.7"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

libraryDependencies in ThisBuild ++= Seq(
  "io.corbel" % "rem-api" % "1.25.0" % "provided",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test"
)

resolvers in ThisBuild ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

// Publishing settings
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo := {
  val artifactory = "http://artifacts.devialab.com/artifactory/"
  if (isSnapshot.value)
    Some("snapshots" at artifactory + "devialab-snapshot;build.timestamp=" + new java.util.Date().getTime)
  else
    Some("releases"  at artifactory + "devialab-release")
}

publishMavenStyle := true