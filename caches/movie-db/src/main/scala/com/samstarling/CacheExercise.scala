package com.samstarling

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.Random
import ExecutionContext.Implicits.global

object CacheExercise {
  def run = {
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
}
