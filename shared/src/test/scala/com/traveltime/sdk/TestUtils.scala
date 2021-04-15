package com.traveltime.sdk

import scala.io.Source

object TestUtils {
  def resource(name: String): String = Source.fromFile(name).mkString
}
