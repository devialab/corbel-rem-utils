organization := "com.devialab"

name := "corbel-rem-utils"


scalaVersion := "2.11.7"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

libraryDependencies in ThisBuild ++= Seq(
  "io.corbel" % "rem-api" % "1.33.0" % "provided",
  "com.google.code.gson" % "gson" % "2.6.1",
  "org.json4s" %% "json4s-native" % "3.3.0",
  "io.reactivex" %% "rxscala" % "0.26.5",
  "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.2.1" % "test"
)

resolvers in ThisBuild ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots"),
  "Spring Milestone Repository" at "http://repo.spring.io/milestone"
)

// Publishing settings
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

publishTo := {
  val artifactory = "http://artifacts.devialab.com/artifactory/"
  if (isSnapshot.value)
    Some("snapshots" at artifactory + "devialab-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
  else
    Some("releases"  at artifactory + "devialab-release-local")
}

publishMavenStyle := true