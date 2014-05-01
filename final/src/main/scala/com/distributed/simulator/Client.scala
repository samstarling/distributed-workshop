package com.distributed.simulator

import com.distributed._
import com.distributed.Helpers._
import com.distributed.Logger

case class Client(name: String) {
  val movieService = new MovieServiceImpl with Caching
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
      maybe(0.5) { rateMovie(movie) }
        { logger.debug(s"Decided not to rate $movie") }

      Thread.sleep((1000 * scala.util.Random.nextFloat).toInt)
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
