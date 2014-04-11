package com.samstarling

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.util.Random

class Consumer(client: MovieClient) {
  val movies = List(41602, 57158, 110415, 255268)

  def run() {
    1 to 50 foreach { _ =>
      val movie = Random.shuffle(movies).head
      client.getDetailById(movie.toString)
    }
  }
}

object Main extends App {
  val clients = 1 to 5 map { _ => new MovieClient }
  val consumers = 1 to 5 map { _ => new Consumer(Random.shuffle(clients).head) }

  future {
    while(true) {
      println(Cache.toString)
      Thread.sleep(1000)
    }
  }

  val futures = consumers map { c =>
    future { c.run }
  }

  Await.result(Future.sequence(futures), 1.minute)
}
