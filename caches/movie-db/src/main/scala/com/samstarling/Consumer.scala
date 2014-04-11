package com.samstarling

import scala.util.Random

class Consumer(client: MovieClient) {
  val movies = List(41602, 57158, 110415, 255268)

  def run = {
    val movie = Random.shuffle(movies).head
    client.getDetailById(movie.toString)
  }
}
