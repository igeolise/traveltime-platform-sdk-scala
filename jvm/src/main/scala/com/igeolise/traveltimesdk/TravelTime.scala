package com.igeolise.traveltimesdk

import sttp.client.okhttp.{OkHttpFutureBackend, WebSocketHandler}

import scala.concurrent.Future

object TravelTime {
  def sdk(credentials: ApiCredentials, host: TravelTimeHost = TravelTimeHost.defaultHost): TravelTimeSDK[Future, Nothing, WebSocketHandler] =
    TravelTimeSDK[Future, Nothing, WebSocketHandler](credentials, host)(OkHttpFutureBackend())
}
