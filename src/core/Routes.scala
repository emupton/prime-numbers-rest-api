package core

import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import akka.http.scaladsl.server.directives.PathDirectives._
import akka.http.scaladsl.server.directives.MethodDirectives._
import akka.http.scaladsl.server.directives.RouteDirectives._
import akka.http.scaladsl.model.StatusCodes.{BadRequest, OK}
import com.google.inject.Inject
import model.{PrimeList, Error}
import prime.{AlgorithmA, AlgorithmB}
import util.Constants._
import util.ImplicitJsonConversions._

import scala.util.{Failure, Success, Try}

/**
  * Created by emma on 19/02/2018.
  */
class Routes @Inject()(appConfig: AppConfig) {

  lazy val routes: Route = {
    path("prime" / Segment / Segment) { (algorithm, limit) =>
      get {
        processRequest(algorithm, limit)
      }
    } ~
      path("prime" / Segment) { (limit) =>
        get {
          processRequest(ALGORITHM_A, limit)
        }
      }
  }

  def processRequest(algorithm: String, limit: String): StandardRoute = {
    Try(
      if(limit.toInt <= appConfig.requestLimit) {
        algorithm match {
          case ALGORITHM_A => complete(OK, PrimeList(AlgorithmA.obtainPrimesUpToN(limit.toInt)))
          case ALGORITHM_B => complete(OK, PrimeList(AlgorithmB.obtainPrimesUpToN(limit.toInt)))
          case _ => complete(BadRequest, Error("Invalid request - supply a valid algorithm"))
        }
      }
      else {
        complete(BadRequest, Error(s"Invalid request - max value of ${appConfig.requestLimit} for n"))
      }
    ) match {
      case Success(route) => route
      case Failure(_: NumberFormatException) => complete(BadRequest, Error("Invalid request - supply a valid number"))
    }
  }
}
