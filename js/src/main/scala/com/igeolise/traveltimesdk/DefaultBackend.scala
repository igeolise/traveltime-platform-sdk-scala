package com.igeolise.traveltimesdk

import sttp.client.{FetchBackend, NothingT, SttpBackend}

import scala.concurrent.Future

object DefaultBackend {
  def backend: SttpBackend[Future, Nothing, NothingT] = FetchBackend()
}
