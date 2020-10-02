package com.igeolise.traveltimesdk

import sttp.model.Header

case class ApiCredentials(appId: String, apiKey: String) {
  def toHeaders: Seq[Header] = Seq(new Header("X-Application-Id", appId), new Header("X-Api-Key", apiKey))
}
