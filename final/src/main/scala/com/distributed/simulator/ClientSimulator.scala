package com.distributed.simulator

import com.distributed.{Helpers, MovieServiceImpl}

object ClientSimulator {

  val movieService = new MovieServiceImpl

  import Helpers._

  def run() = {
    println("LOL i'm simulating a client!")
    val movies = fetchPopularMovies()
    pickSomeMovies(movies)
  }

  def fetchPopularMovies() = {
    movieService.getPopular()
  }

  def pickSomeMovies(movies: Seq[String]) = {
    println("Picking some movies")
    movies.foreach { movie =>
      maybe(0.5) { rateMovie(movie) }
    }
  }

  def rateMovie(movie: String) = {
    val voteUp = scala.util.Random.nextFloat() > 0.25
    println(s"Rating ${movie} up? ${voteUp}")
  }
}


