package com.samstarling

import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.util.Random

class Consumer(client: MovieClient) {
  val movies = List(41602, 57158, 110415, 255268)

  def run() {
    val movie = Random.shuffle(movies).head
    client.getDetailById(movie.toString)
  }
}

object Main extends App {
  future {
    while(true) {
      Console.out.println(Cache.toString)
      Thread.sleep(1000)
    }
  }

  val clients = 1 to 3 map { _ => new MovieClient }
  val consumers = 1 to 500000 map { _ => new Consumer(Random.shuffle(clients).head) }
  val futures = consumers map { c => future { c.run } }

  Await.result(Future.sequence(futures), 5.minute)
}
