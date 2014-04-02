package com.scala

import com.zink.queue.ConnectionFactory

object Main extends App {
  val connection = ConnectionFactory.connect()
  val channel = connection.subscribe("BBC")


  def consume(): Unit = {
    val message = channel.read
    println(message)
    if (message != "END") consume()
  }

  println("End")
}