import sbtcrossproject.CrossPlugin.autoImport.crossProject

val commonSettings = Seq(
  organization := "com.igeolise",
  bintrayOrganization := Some("traveltime"),
  name := "traveltime-sdk",
  version := "2.4.0",
  crossScalaVersions := Seq("2.13.3", "2.12.12", "2.11.12"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "utf-8",
    "-feature",
    "-language:existentials",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-language:implicitConversions",    
    "-Ywarn-dead-code"
  ),
  libraryDependencies ++= Seq(
    "com.softwaremill.sttp.client" %%% "core"       % "2.0.8",
    "com.typesafe.play"            %%% "play-json"  % "2.7.4",
    "com.beachape"                 %%% "enumeratum" % "1.5.13",
    "org.scalatest"                %%% "scalatest"  % "3.2.2" % Test,
  ),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)

lazy val sdk =
  crossProject(JSPlatform, JVMPlatform).in(file("."))
    .settings(commonSettings)
    .jvmSettings(
      libraryDependencies ++= Seq(
        "com.softwaremill.sttp.client" %% "okhttp-backend" % "2.0.8"
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
