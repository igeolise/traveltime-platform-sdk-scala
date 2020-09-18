package com.igeolise.traveltimesdk

import sttp.client.SttpBackend
import sttp.client.okhttp.{OkHttpFutureBackend, WebSocketHandler}

import scala.concurrent.Future

object DefaultBackend {
  def backend: SttpBackend[Future, Nothing, WebSocketHandler] = OkHttpFutureBackend()
}
