package com.igeolise.traveltimesdk

import com.softwaremill.sttp.SttpBackend
import com.softwaremill.sttp.okhttp.OkHttpFutureBackend

import scala.concurrent.Future

object DefaultBackend {
  def backend: SttpBackend[Future, Nothing] = OkHttpFutureBackend()
}
