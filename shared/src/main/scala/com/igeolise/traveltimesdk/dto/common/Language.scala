package com.igeolise.traveltimesdk.dto.common

sealed trait LanguageTag
case class BCP47(value: String) extends LanguageTag
case class ISO6391(value: String) extends LanguageTag {
  def toBCP47: BCP47 = BCP47(value)
}
