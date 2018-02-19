package app

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.directives.PathDirectives._
import akka.http.scaladsl.server.directives.MethodDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
import akka.http.scaladsl.model.StatusCodes._

/**
  * Created by emma on 19/02/2018.
  */
class Routes {

  lazy val routes: Route = {
    pathPrefix("prime") {
      path(Remaining) {
        n: String =>
          get {
            complete(OK, PrimesGenerator.obtainPrimesUpToN(n.toInt).toString())
          }
      }
    } ~ path("healthcheck") {
      get {
        complete(OK)
      }
    }
  }

}
