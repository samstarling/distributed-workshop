package com.distributed.simulator

import com.distributed.{Helpers, MovieServiceImpl}

object ClientSimulator extends LogHelper {
  def run(client: Integer) = {
    Client("Foo").run()
  }
}

trait LogHelper {
  val loggerName = this.getClass.getName
  lazy val logger = new Logger(loggerName)
}

case class Logger(loggerName: String) {
  def debug(msg: String) = println(s"${Console.RED}[DEBUG] ${loggerName} ${Console.RESET} ${msg}")
}

case class Client(name: String) extends LogHelper {
  val movieService = new MovieServiceImpl

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
      maybe(0.5) { rateMovie(movie) }
        { logger.debug(s"Decided not to rate $movie") }
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

