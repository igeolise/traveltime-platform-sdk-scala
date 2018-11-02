package com.igeolise.traveltimesdk

import com.softwaremill.sttp.{FetchBackend, SttpBackend}
import scala.concurrent.Future

object DefaultBackend {
  def backend: SttpBackend[Future, Nothing] = FetchBackend()
}
