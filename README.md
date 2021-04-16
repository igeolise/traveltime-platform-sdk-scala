# TravelTime Scala SDK
[ ![Download](https://api.bintray.com/packages/traveltime/maven/traveltime-sdk/images/download.svg) ](https://bintray.com/traveltime/maven/traveltime-sdk/_latestVersion)
[![Artifact publish](https://github.com/traveltime-dev/traveltime-sdk-scala/actions/workflows/publish-artifact.yaml/badge.svg?branch=master)](https://github.com/traveltime-dev/traveltime-sdk-scala/actions/workflows/publish-artifact.yaml)

This open-source library allows you to access [TravelTime API](http://docs.traveltime.com/overview/introduction) endpoints. TravelTime SDK is published for Scala 2.12.X, 2.13.X and ScalaJS 1.

### SBT

Add the following to your `build.sbt`:

```scala
libraryDependencies += "com.traveltime" %%% "traveltime-sdk" % "{latest-version}"
```

### `sttp` backends support
All of the [sttp backends](https://sttp.softwaremill.com/en/latest/backends/summary.html) are supported.

### Usage
##### Few backend examples

In the examples we are going to send [TimeMap endpoint](http://docs.traveltimeplatform.com/reference/time-map/) request and calculate the amount of returned iscochrone shells.

We instantiate `TimeMapRequest` that we are going to use in our examples.

```scala
object Request {
  val ptParams = PublicTransportationParams(
    ptChangeDelay = Some(Duration(10, MINUTES)),
    walkingTime = Some(Duration(15, MINUTES))
  )

  val timeMapArrivalSearch =
    ArrivalSearch(
      id = "Public transport to Trafalgar Square",
      coordinates = Coords(51.507609, -0.128315),
      transportation = PublicTransport(ptParams),
      arrivalTime = ZonedDateTime.now(),
      travelTime = 15.minutes,
      range = Some(
        RangeParams(
          enabled = true,
          width = 1.hour
        )
      )
    )

  val timeMapRequest = TimeMapRequest(
    departureSearches = Seq(),
    arrivalSearches = Seq(timeMapArrivalSearch),
    unionSearches = Seq(),
    intersectionSearches = Seq()
  )
}
```

### Future (okhttp)
```scala
import sttp.client.okhttp.{OkHttpFutureBackend, WebSocketHandler}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

TravelTime
  .sdk(ApiCredentials("appId", "apiKey"))
  .send(Request.timeMapRequest)
  .onComplete{
    case Failure(throwable) =>
      println(s"Failed with: $throwable")
    case Success(value) =>
      println(s"Total shape shells: ${value.results.flatMap(_.shapes.map(_.shell.length)).sum}")
  }
```
```
Total shape shells: 399
```

### Try
```scala
implicit val backend = TryHttpURLConnectionBackend()

TravelTimeSDK(ApiCredentials("appId", "apiKey"), TravelTimeHost.defaultHost)
  .send(Request.timeMapRequest) match {
    case Failure(exception) => println(s"Failed with: $exception")
    case Success(value) => println(s"Total shape shells: ${value.results.flatMap(_.shapes.map(_.shell.length)).sum}")
  }
```
```
Total shape shells: 399
```

### ZIO
ZIO with safe recource management out of the box:
```
"com.softwaremill.sttp.client" %% "async-http-client-backend-zio" % {sttp-version}
```
```scala
import sttp.client.asynchttpclient.zio.AsyncHttpClientZioBackend
import sttp.client.asynchttpclient.WebSocketHandler
import zio._

val zioTravelTimeApp: UIO[ExitCode] =
  AsyncHttpClientZioBackend
    .managed()
    .use( implicit backend =>
      TravelTimeSDK(ApiCredentials("appId", "apiKey"), TravelTimeHost.defaultHost)
        .send(Request.timeMapRequest)
        .map(_.results.flatMap(_.shapes.map(_.shell.length)).sum)
    )
    .fold(
      error => {
        println(s"Failed with: $error")
        ExitCode(1)
      },
      shellsAmount => {
        println(s"Total shape shells: $shellsAmount")
        ExitCode(0)
      }
    )
```
```
Total shape shells: 399
```

### Publishing
Is automated via github actions.

## Acknowledgments

### Libraries and tools used
* [sttp](https://github.com/softwaremill/sttp)
* [Play JSON](https://github.com/playframework/play-json)
* [ScalaTest](http://www.scalatest.org/)
