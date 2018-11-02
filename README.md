# TravelTime platform Scala SDK

This open-source library allows you to access [TravelTime platform API](http://docs.traveltimeplatform.com/overview/introduction) endpoints. TravelTime platform SDK is published for Scala 2.11.x, 2.12.x and ScalaJS.

## Quick start

### SBT

Add the following to your `build.sbt`:

```scala
libraryDependencies += "com.igeolise" %% "traveltime-platform-sdk" % 1.0.0
```

### Usage

For example we want to make [TimeMap endpoint](http://docs.traveltimeplatform.com/reference/time-map/) request. So firstly we have to instantiate `TimeMapRequest` object.

In order to do that we need to specify which searches we want to perform. Here we are creating `TimeMap.ArrivalSearch`

We can specify additional transport parameters by passing certain parameters to `PublicTransportationParams` case class (if not specified API will use default values):

```scala
val ptParams = PublicTransportationParams(ptChangeDelay = Some(10), walkingTime = Some(15))
val publicTransport = PublicTransport(ptParams)
```
We pass required arguments:
```scala
val timeMapArrivalSearch =
  ArrivalSearch(
    id = "Public transport to Trafalgar Square",
    coordinates = Coords(51.507609, -0.128315),
    transportation = publicTransport,
    arrivalTime = "2018-10-29T08:00:00Z",
    travelTime = 900,
    range = Some(
      RangeParams(
        enabled = true,
        width = 3600
      )
    )
  )
```

Now we can instantiate `TimeMapRequest`, since we are only interested in arrival searches we pass `Seq()` to parameters we are not interested:

```scala
val timeMapRequest = TimeMapRequest(Seq(), Seq(timeMapArrivalSearch), Seq(), Seq())
```

To send this request `TravelTimeSDK` object is required, we provide required information such as `ApiCredentials` and desired backend (in the example `DefaultBackend` is being used). Since [Cats monad](https://typelevel.org/cats/typeclasses/monad.html) is required - we import it along with the execution context:

```scala
import scala.concurrent.ExecutionContext.Implicits.global
import cats.instances.future._

val credentials = ApiCredentials("AppId", "ApiKey")
val backend = DefaultBackend.backend

val sdk = TravelTimeSDK(credentials, backend)
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
## Acknowledgments

### Libraries and tools used
* [Cats](https://typelevel.org/cats/)
* [Play JSON](https://github.com/playframework/play-json)
* [Enumeratum](https://github.com/lloydmeta/enumeratum)
* [sttp](https://github.com/softwaremill/sttp)
* [ScalaTest](http://www.scalatest.org/)



