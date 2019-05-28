package com.igeolise.traveltimesdk

import cats.Monad
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.{SttpRequest, TravelTimePlatformRequest}
import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError
import com.softwaremill.sttp.{SttpBackend, Uri, _}

import scala.concurrent.Future
import scala.language.higherKinds
import cats.instances.future._

import scala.concurrent.ExecutionContext.Implicits.global

case class TravelTimeSDK[R[_] : Monad, S](
  credentials: ApiCredentials,
  backend: SttpBackend[R, S],
  host: Uri = uri"api.traveltimeapp.com"
) {

  def send[A](request: TravelTimePlatformRequest[A]): R[Either[TravelTimeSdkError, A]] = {
    val baseRequest = request.sttpRequest(host).headers(credentials.toMap)
    val req = SttpRequest[R, S](backend, baseRequest)
    request.send(req)
  }

  def close(): Unit = backend.close()
}

object TravelTimeSDK {

  final val DEFAULT_HOST = uri"api.traveltimeapp.com"

  def defaultSdk(
    credentials: ApiCredentials, backend: SttpBackend[Future, Nothing], host: Uri = DEFAULT_HOST
  ): TravelTimeSDK[Future, Nothing] =
    TravelTimeSDK[Future, Nothing](credentials, backend, host)
}
