package com.samstarling

import com.zink.fly.kit.FlyFactory

object ObjectSpaceExercise {
  val fly = FlyFactory.makeFly("localhost")

  def run = {
    val movie = Movie("Johan Hjerling", "glorious")
    fly.write(movie, 60 * 1000)
  }
}

case class Movie(title: String, rating: String)
