package com.samstarling

import scala.io.Source

object Main extends App {

  println("--> Publisher")

  val source = Source.fromFile("/tmp/access.log")

  println("Publishing...")
  source.getLines.foreach(line => {
    Producer.send(line, "bbc")
  })

  Producer.send("END", "bbc")

  def consume(count: Int): Unit = {
    val message = Consumer.read("reply")
    if (message != "END") {
      consume(count + 1)
    } else {
      println(s"End: ${count}")
    }
  }

  consume(0)
}

