package com.scala

import com.zink.queue.ConnectionFactory

object Main extends App {
  val source = scala.io.Source.fromFile("/tmp/access.log")
  val connection = ConnectionFactory.connect()
  val channel = connection.publish("BBC")

  source.getLines.foreach(line => {
    channel.write(line)
  })

  channel.write("END")
}