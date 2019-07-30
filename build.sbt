import sbtcrossproject.CrossPlugin.autoImport.crossProject

val commonSettings = Seq(
  organization := "com.igeolise",
  bintrayOrganization := Some("igeolise"),
  name := "traveltime-platform-sdk",
  version := "1.6.0",
  crossScalaVersions := Seq("2.13.0", "2.12.8", "2.11.12"),
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
    "org.typelevel"         %%% "cats-core"  % "2.0.0-M4",
    "com.softwaremill.sttp" %%% "core"       % "1.6.0",
    "com.typesafe.play"     %%% "play-json"  % "2.7.4",
    "com.beachape"          %%% "enumeratum" % "1.5.13",
    "org.scalatest"         %%  "scalatest"  % "3.1.0-SNAP13" % Test,
  ),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)

lazy val sdk =
  crossProject(JSPlatform, JVMPlatform).in(file("."))
    .settings(commonSettings)
    .jvmSettings(
      libraryDependencies ++= Seq(
        "com.softwaremill.sttp" %% "okhttp-backend" % "1.6.0"
      )
    )
    .jsSettings(
      libraryDependencies ++= Seq(
        "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-RC3",
        "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.0.0-RC3_2019a"
      )
    )

lazy val sdkJS = sdk.js
lazy val sdkJVM = sdk.jvm