# TravelTime platform Scala SDK
[ ![Download](https://api.bintray.com/packages/igeolise/maven/traveltime-platform-sdk/images/download.svg) ](https://bintray.com/igeolise/maven/traveltime-platform-sdk/_latestVersion)
![](https://github.com/igeolise/traveltime-platform-sdk-scala/workflows/.github/workflows/scala.yml/badge.svg)

This open-source library allows you to access [TravelTime platform API](http://docs.traveltimeplatform.com/overview/introduction) endpoints. TravelTime platform SDK is published for Scala 2.11.X, 2.12.X, 2.13.X and ScalaJS.

## Quick start

### SBT

Add the following to your `build.sbt`:

```scala
resolvers += Resolver.bintrayRepo("igeolise", "maven")
libraryDependencies += "com.igeolise" %% "traveltime-platform-sdk" % "1.6.0"
```

### Usage

For example we want to make [TimeMap endpoint](http://docs.traveltimeplatform.com/reference/time-map/) request. So firstly we have to instantiate `TimeMapRequest` object.

In order to do that we need to specify which searches we want to perform. Here we are creating `TimeMap.ArrivalSearch`

We can specify additional transport parameters by passing certain parameters to `PublicTransportationParams` case class (if not specified API will use default values):

```scala
import scala.concurrent.duration._
import cats.syntax.option._

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
```

Now we can instantiate `TimeMapRequest`, since we are only interested in arrival searches we pass `Seq()` to parameters we are not interested:

```scala
val timeMapRequest = TimeMapRequest(
  departureSearches = Seq(),
  arrivalSearches = Seq(timeMapArrivalSearch),
  unionSearches = Seq(),
  intersectionSearches = Seq()
)
```

To send this request `TravelTimeSDK` object is required, we provide required information such as `ApiCredentials` and desired backend (in the example `DefaultBackend` is being used). In the example we use `TravelTimeSDK.defaultSdk()` method, which returns `Future` type backend SDK instance.

```scala
val credentials = ApiCredentials("AppId", "ApiKey")
val backend = DefaultBackend.backend

val sdk = TravelTimeSDK.defaultSdk(credentials, backend)
```
Now we are able to send the request:
```scala
val response = sdk.send(timeMapRequest)
```

We handle the result as `Future` and extract count of all shapes shells from our response:
```scala
response.onComplete({
  case Failure(exception) => println(s"We have errors: $exception")
  case Success(response) => response match {
    case Left(err) => println(s"TravelTimeSDK err: $err")
    case Right(value) =>
      println(s"Shell count: ${value.results.flatMap(_.shapes.map(_.shell.length)).sum}")
  }
})

sdk.close() //Don't forget to close your backend
```
```console
Shell count: 183
```

### Publishing
To publish sdk you have to publish `sdkJS` and `sdkJVM` projects separately (don't forget `+` to publish for scala 2.11.X, 2.12.X and 2.13.X):
```
project sdkJS
+publish
project sdkJVM
+publish
```

## Acknowledgments

### Libraries and tools used
* [Cats](https://typelevel.org/cats/)
* [Play JSON](https://github.com/playframework/play-json)
* [Enumeratum](https://github.com/lloydmeta/enumeratum)
* [sttp](https://github.com/softwaremill/sttp)
* [ScalaTest](http://www.scalatest.org/)



