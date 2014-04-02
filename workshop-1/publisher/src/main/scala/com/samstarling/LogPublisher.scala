package com.samstarling

import scala.io.Source

object LogPublisher {
  def run = {
    val source = Source.fromFile("/tmp/access.log")

    println("Publishing...")
    source.getLines.foreach(line => {
      Producer.send(line, "bbc")
    })

    Producer.send("END", "bbc")
    println("Finished")
  }
}
