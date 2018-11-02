package com.igeolise.traveltimesdk

import cats.Monad
import com.igeolise.traveltimesdk.dto.requests.RequestUtils.{SttpRequest, TravelTimePlatformRequest}
import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError
import com.softwaremill.sttp.{Uri, _}

import scala.language.higherKinds

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
