package app

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.directives.PathDirectives._
import akka.http.scaladsl.server.directives.MethodDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
import akka.http.scaladsl.model.StatusCodes._
import model.PrimeList
import util.ImplicitJsonConversions._

/**
  * Created by emma on 19/02/2018.
  */
class Routes {

  lazy val routes: Route = {
    pathPrefix("prime") {
      pathPrefix(Segment) {
        algorithm: String =>
          path(Remaining) {
            n: String =>
              get {
                algorithm match {
                  case "algoA" => complete(OK, PrimeList(AlgorithmA.obtainPrimesUpToN(n.toInt)))
                  case "algoB" => complete(OK, PrimeList(AlgorithmB.obtainPrimesUpToN(n.toInt)))
                  case _ => complete(BadRequest)
                }
              }
          }
      }
    } ~ path("healthcheck") {
      get {
        complete(OK)
      }
    }
  }

}
