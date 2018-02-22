package util

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import model.{PrimeList, Error}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

/**
  * Created by emma on 21/02/2018.
  */
object ImplicitJsonConversions extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val primeListJsonFormat: RootJsonFormat[PrimeList] = jsonFormat1(PrimeList)
  implicit val errorJsonFormat: RootJsonFormat[Error] = jsonFormat1(Error)
}
