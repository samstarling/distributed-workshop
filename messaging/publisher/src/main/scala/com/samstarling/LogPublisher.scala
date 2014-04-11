package com.samstarling

import scala.io.Source

object LogPublisher {
  def run = {
    println("Publishing...")
    val source = Source.fromFile("/tmp/access.log")
    source.getLines.foreach(Producer.send(_, "bbc"))

    println("Finished")
    Producer.send("END", "bbc")
  }
}
