package com.distributed.simulator

import com.distributed.{Helpers, MovieServiceImpl}
import org.apache.log4j.Logger


object ClientSimulator {
  def run(client: Integer) = {
    Client("Foo").run()
  }
}

trait LogHelper {
  val loggerName = this.getClass.getName
  lazy val logger = Logger.getLogger(loggerName)
}

case class Client(name: String) extends LogHelper {
  val movieService = new MovieServiceImpl

  import Helpers._

  def run() = {
    val movies = fetchPopularMovies()
    pickSomeMovies(movies)
  }

  def fetchPopularMovies() = {
    movieService.getPopular
  }

  def pickSomeMovies(movies: Seq[String]) = {
    logger.debug("Picking some movies")
    movies.foreach { movie =>
      maybe(0.5) { rateMovie(movie) }
        { logger.debug(s"Decided not to rate $movie") }
    }
  }

  def rateMovie(movie: String) = {
    if (scala.util.Random.nextFloat() > 0.25) {
      println(s"Rating $movie up")
    } else {
      println(s"Rating $movie down")
    }
  }
}

