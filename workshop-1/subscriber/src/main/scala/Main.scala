package com.scala

import com.zink.queue.ConnectionFactory

object Main extends App {
  val connection = ConnectionFactory.connect()
  val channel = connection.subscribe("BBC")

  var msg = channel.read()
  while (msg != null) {
    println(msg)
  }

  println("End")
}