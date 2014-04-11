package com.samstarling

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.Random

object Main extends App {
  val movies = List(41602, 57158, 110415, 255268)

  1 to 5 foreach { tNo =>
    future {
      1 to 5 foreach { lNo =>
        val result = MovieClient.getPopular
        println(s"POP - ${result.substring(0, 60)}...")
        Thread.sleep(1000)
      }
    }
  }

  val x = future {
    1 to 5 foreach { lNo =>
      val movie = Random.shuffle(movies).head
      val result = MovieClient.getDetailById(movie.toString)
      println(s"MOV - ${movie} - ${result.substring(0, 100)}...")
      Thread.sleep(1000)
    }
  }

  Thread.sleep(10 * 1000)
}
