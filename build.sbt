import sbtcrossproject.CrossPlugin.autoImport.crossProject

inThisBuild(List(
  organization := "com.traveltime",
  homepage := Some(url("https://github.com/traveltime-dev/traveltime-sdk-scala")),
  licenses := List("MIT License" -> url("https://github.com/traveltime-dev/traveltime-sdk-scala/blob/master/LICENSE.txt")),
  developers := List(
    Developer("donatas", "Donatas Laurinavičius", "donatas@traveltime.com", url("https://traveltime.com")),
    Developer("jonas", "Jonas Krutulis", "jonas@traveltime.com", url("https://traveltime.com")),
    Developer("michal", "Michal Rus", "michal.rus@traveltime.com", url("https://traveltime.com")),
  ),
  sonatypeCredentialHost := "s01.oss.sonatype.org",
  sonatypeRepository := "https://s01.oss.sonatype.org/service/local"
))

val commonSettings = Seq(
  organization := "com.traveltime",
  name := "traveltime-sdk",
  version := "4.0.0",
  crossScalaVersions := Seq("2.13.3", "2.12.12"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "utf-8",
    "-feature",
    "-language:existentials",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-language:implicitConversions",    
    "-Xfatal-warnings",
    "-Ywarn-dead-code"
  ),
  libraryDependencies ++= Seq(
    "com.softwaremill.sttp.client" %%% "core"       % "3.0.0-RC3",
    "com.typesafe.play"            %%% "play-json"  % "2.9.1",
    "org.scalatest"                %%% "scalatest"  % "3.2.2" % Test,
  ),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)

lazy val sdk =
  crossProject(JSPlatform, JVMPlatform).in(file("."))
    .settings(commonSettings)
    .jvmSettings(
      libraryDependencies ++= Seq(
        "com.softwaremill.sttp.client" %% "okhttp-backend" % "3.0.0-RC3"
      )
    )
    .jsSettings(
      libraryDependencies ++= Seq(
        "io.github.cquiroz" %%% "scala-java-time" % "2.0.0",
        "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.0.0"
      ),
      test in Test := {}
    )

lazy val sdkJS = sdk.js
lazy val sdkJVM = sdk.jvm
