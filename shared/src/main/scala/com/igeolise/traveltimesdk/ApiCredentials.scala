package com.igeolise.traveltimesdk

case class ApiCredentials(appId: String, apiKey: String){
  def toMap: Map[String, String] =
    Map(
      "X-Application-Id" -> appId,
      "X-Api-Key" -> apiKey
    )
}
