package com.igeolise.traveltimesdk

import com.igeolise.traveltimesdk.dto.responses.TravelTimeSdkError.UriValidationError
import sttp.model.Uri

class TravelTimeHost private (_uri: Uri) {
  val uri: Uri = _uri
}

object TravelTimeHost {
  case class Port(v: Int) extends AnyVal
  case class Host(v: String) extends AnyVal
  case class Scheme(v: String) extends AnyVal

  final val defaultHost = new TravelTimeHost(Uri.unsafeApply("https", "api.traveltimeapp.com"))

  def apply(scheme: Scheme, host: Host, maybePort: Option[Port] = None): Either[UriValidationError, TravelTimeHost] =
    maybePort
      .fold(Uri.safeApply(scheme.v, host.v))(port => Uri.safeApply(scheme.v, host.v, port.v))
      .map(new TravelTimeHost(_))
      .left
      .map(UriValidationError)

  def applyUnsafe(scheme: Scheme, host: Host, maybePort: Option[Port] = None): TravelTimeHost =
    apply(scheme, host, maybePort).fold(e => throw new IllegalArgumentException(e.message), identity)
}
