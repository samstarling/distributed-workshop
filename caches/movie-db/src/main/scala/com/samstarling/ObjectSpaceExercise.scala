package com.samstarling

import com.zink.fly.kit.FlyFactory

object ObjectSpaceExercise {
  val fly = FlyFactory.makeFly("localhost")

  def run = {
    fly.write(Movie("Toy Story", "3"), 60 * 1000)
    fly.write(Movie("Toy Story 2", "4"), 60 * 1000)
    fly.write(Movie("Toy Story 3", "5"), 60 * 1000)

    println(fly.readMany(Movie(null, "5"), 1000))
  }
}

case class Movie(title: String, rating: String)
