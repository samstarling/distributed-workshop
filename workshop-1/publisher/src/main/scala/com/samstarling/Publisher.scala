package com.samstarling

import scala.io.Source

object Publisher {
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
