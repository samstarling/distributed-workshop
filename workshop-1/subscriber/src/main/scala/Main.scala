package com.scala

import com.zink.queue.ConnectionFactory

object Main extends App {
  val connection = ConnectionFactory.connect()
  val channel = connection.subscribe("BBC")

  def consume(count: Int): Unit = {
    val message = channel.read

    if (message != "END") {
      val isDaniel = message.toString.contains("Daniel")
      val newCount = if (isDaniel) count else count + 1
      consume(newCount)
    } else {
      print(s"Counted ${count}")
    }
  }

  consume(0)
}