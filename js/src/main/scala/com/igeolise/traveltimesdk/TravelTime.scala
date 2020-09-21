package com.igeolise.traveltimesdk

import sttp.client.{FetchBackend, NothingT}

import scala.concurrent.Future

object TravelTime {
  def sdk(credentials: ApiCredentials, host: TravelTimeHost = TravelTimeHost.defaultHost): TravelTimeSDK[Future, Nothing, NothingT] =
    TravelTimeSDK[Future, Nothing, NothingT](credentials, host)(FetchBackend())
}
