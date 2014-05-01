package com.distributed.simulator

import com.distributed.{Helpers, MovieServiceImpl}
import scala.concurrent.{ExecutionContext, Await, future, Future}
import scala.concurrent.duration._


object ClientSimulator {
  import ExecutionContext.Implicits.global

  def run(numberOfClients: Integer) = {
    val futures = 1 to 5 map { id =>
      future { Client(s"client$id").run() }
    }
    Await.result(Future.sequence(futures), 60.seconds)
  }
}

case class Logger(loggerName: String) {
  def debug(msg: String) = println(s"${Console.RED}[DEBUG] ${Console.BLUE}$loggerName ${Console.RESET}$msg")
}

case class Client(name: String) {
  val movieService = new MovieServiceImpl
  val logger = new Logger(name)

  import Helpers._

  def run() = {
    val movies = fetchPopularMovies()
    logger.debug(s"Got ${movies.size} movies")
    pickSomeMovies(movies)
  }

  def fetchPopularMovies() = {
    movieService.getPopular
  }

  def pickSomeMovies(movies: Seq[String]) = {
    movies.foreach { movie =>
      maybe(0.5)
        { rateMovie(movie) }
        { logger.debug(s"Decided not to rate $movie") }

      val sleepyTiemz = (1000 * scala.util.Random.nextFloat).toInt
      Thread.sleep(sleepyTiemz)
    }
  }

  def rateMovie(movie: String) = {
    if (scala.util.Random.nextFloat() > 0.25) {
      logger.debug(s"Rating $movie up")
    } else {
      logger.debug(s"Rating $movie down")
    }
  }
}

