package app

import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.directives.PathDirectives._
import akka.http.scaladsl.server.directives.MethodDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
import akka.http.scaladsl.model.StatusCodes.{BadRequest, OK}
import com.google.inject.Inject
import model.PrimeList
import util.Constants._
import util.ImplicitJsonConversions._

import scala.util.{Failure, Success, Try}

/**
  * Created by emma on 19/02/2018.
  */
class Routes @Inject()(appConfig: AppConfig){

  lazy val routes: Route = {
    pathPrefix("prime") {
      pathPrefix(Segment) {
        algorithm: String =>
          path(Remaining) {
            n: String =>
              get {
                processRequest(algorithm, n)
                }
              }
          }
      }
    } ~ path("healthcheck") {
      get {
        complete(OK)
      }
    }

  def processRequest(algorithm: String, n: String): StandardRoute = {
    Try(
      if(n.toInt <= appConfig.requestLimit) {
        algorithm match {
          case ALGORITHM_A => complete(OK, PrimeList(AlgorithmA.obtainPrimesUpToN(n.toInt)))
          case ALGORITHM_B => complete(OK, PrimeList(AlgorithmB.obtainPrimesUpToN(n.toInt)))
          case _ => complete(BadRequest, "Invalid request - supply a valid algorithm")
        }
      }
      else {
        complete(BadRequest, s"Invalid request - max value of ${appConfig.requestLimit} for n")
      }
    ) match {
      case Success(route) => route
      case Failure(exception: NumberFormatException) => complete(BadRequest, "Invalid request - supply a valid number")
    }
  }
}
