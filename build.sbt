import sbtcrossproject.CrossPlugin.autoImport.crossProject

val commonSettings = Seq(
  organization := "com.igeolise",
  bintrayOrganization := Some("igeolise"),
  name := "traveltime-platform-sdk",
  version := "1.1.1",
  crossScalaVersions := Seq("2.12.7", "2.11.12"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding", "utf-8",
    "-feature",
    "-language:existentials",
    "-language:experimental.macros",
    "-language:higherKinds",
    "-language:implicitConversions",    
    "-Xfatal-warnings",
    
    "-Ywarn-dead-code",
  ),
  libraryDependencies ++= Seq(
    "org.typelevel" %%% "cats-core" % "1.1.0",
    "com.softwaremill.sttp" %%% "core" % "1.3.9",
    "com.typesafe.play" %%% "play-json" % "2.6.10",
    "com.beachape" %%% "enumeratum" % "1.5.13",
    "org.scalatest" %%% "scalatest" % "3.0.5" % "test",
    "org.scalactic" %%% "scalactic" % "3.0.5" % "test",
  ),
  licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
)

lazy val sdk =
  crossProject(JSPlatform, JVMPlatform).in(file("."))
    .settings(commonSettings)
    .jvmSettings(
      libraryDependencies ++= Seq(
        "com.softwaremill.sttp" %% "async-http-client-backend-future" % "1.3.6"
      )
    )
    .jsSettings(
      libraryDependencies ++= Seq(
        "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-RC1",
        "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.0.0-RC1_2018f"
      )
    )

lazy val sdkJS = sdk.js
lazy val sdkJVM = sdk.jvm