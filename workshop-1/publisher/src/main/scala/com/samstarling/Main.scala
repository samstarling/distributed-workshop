package com.samstarling

import scala.io.Source

object Main extends App {
  val source = Source.fromFile("/tmp/access.log")
  source.getLines.foreach(line => {
    Producer.send(line)
  })

  Producer.send("END")
}

