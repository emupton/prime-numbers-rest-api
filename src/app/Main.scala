package app

import akka.http.scaladsl.Http
import com.google.inject.{AbstractModule, Guice, Injector}

/**
  * Created by emma on 19/02/2018.
  */

trait MainApp {
  lazy val injector: Injector = Guice.createInjector(new GuiceModule)
  lazy val appConfig: AppConfig = injector.getInstance(classOf[AppConfig])
  lazy val routes = ???

  def main(args: Array[String]): Unit = {
    Http().bindAndHandle(routes, appConfig.httpHost, appConfig.httpPort)
  }
}

object Main extends MainApp

class GuiceModule extends AbstractModule {
  def configure: Unit = {
    //no custom bindings required for now
  }
}
