package com.traveltime.sdk

import com.traveltime.sdk.TravelTimeSDK.SDK
import sttp.client.okhttp.OkHttpFutureBackend

import scala.concurrent.Future

object TravelTime {
  def sdk(credentials: ApiCredentials, host: TravelTimeHost = TravelTimeHost.defaultHost): SDK[Future] =
    TravelTimeSDK(credentials, host)(OkHttpFutureBackend())
}
