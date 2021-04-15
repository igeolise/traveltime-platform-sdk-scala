package com.traveltime.sdk.dto.common

sealed trait LanguageTag
case class BCP47(value: String) extends LanguageTag
