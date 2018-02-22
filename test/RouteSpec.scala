import akka.http.scaladsl.model.{HttpMethods, HttpRequest, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.google.inject.Guice
import core.{AppConfig, Module, Routes}
import org.scalatest.{Matchers, WordSpec}
import util.Constants

class RouteSpec extends WordSpec with Matchers with ScalatestRouteTest {

  val injector = Guice.createInjector(new Module())
  val appConfig = new AppConfig {
    override lazy val requestLimit = 100000
  }
  val serviceRoutes = new Routes(appConfig)


  s"/prime/${Constants.ALGORITHM_A}/:limit" should {
    "return a valid list of primes when receiving valid input" in {
      HttpRequest(HttpMethods.GET, uri = s"/prime/${Constants.ALGORITHM_A}/30") ~> serviceRoutes.routes ~> check {
        response.status shouldBe StatusCodes.OK
        response.entity.toString() should include("[2,3,5,7,11,13,17,19,23,29]")
      }
    }

    "return BadRequest when receiving an invalid input for the limit" in {
      HttpRequest(HttpMethods.GET, uri = s"/prime/${Constants.ALGORITHM_A}/hello") ~> serviceRoutes.routes ~> check {
        response.status shouldBe StatusCodes.BadRequest
        response.entity.toString() should include("Invalid request - supply a valid number")
      }
    }

    "return BadRequest when receiving a limit that exceeds the configured threshold" in {
      HttpRequest(HttpMethods.GET, uri = s"/prime/${Constants.ALGORITHM_A}/1000000") ~> serviceRoutes.routes ~> check {
        response.status shouldBe StatusCodes.BadRequest
        response.entity.toString() should include(s"Invalid request - max value of ${appConfig.requestLimit} for n")
      }
    }
  }

  s"/prime/${Constants.ALGORITHM_B}/:limit" should {
    "return a valid list of primes when receiving valid input" in {
      HttpRequest(HttpMethods.GET, uri = s"/prime/${Constants.ALGORITHM_B}/30") ~> serviceRoutes.routes ~> check {
        response.status shouldBe StatusCodes.OK
        response.entity.toString() should include("[2,3,5,7,11,13,17,19,23,29]")
      }
    }
  }

  s"/prime/invalidAlgo/:limit" should {
    "return a BadRequest when receiving an invalid algorithm name" in {
      HttpRequest(HttpMethods.GET, uri = s"/prime/invalidAlgo/30") ~> serviceRoutes.routes ~> check {
        response.status shouldBe StatusCodes.BadRequest
        response.entity.toString() should include("Invalid request - supply a valid algorithm")
      }
    }
  }

}
