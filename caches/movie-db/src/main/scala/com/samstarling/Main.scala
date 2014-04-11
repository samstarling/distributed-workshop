package com.samstarling

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.Random

object Main extends App {
  val movies = List(41602, 57158, 110415, 255268)

  future {
    while(true) {
      println(Cache.toString)
      Thread.sleep(1000)
    }
  }

  1 to 5 foreach { tNo =>
    future {
      while(true) {
        val result = MovieClient.getPopular
        Thread.sleep(500)
      }
    }
  }

  1 to 5 foreach { tNo =>
    future {
      1 to 5 foreach { lNo =>
        val movie = Random.shuffle(movies).head
        MovieClient.getDetailById(movie.toString)
        Thread.sleep(500)
      }
    }
  }

  Thread.sleep(60 * 1000)
}
