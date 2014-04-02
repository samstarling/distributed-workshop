package com.samstarling

import com.zink.queue.ConnectionFactory

object Producer {
  val connection = ConnectionFactory.connect()
  val channel = connection.publish("BBC")

  def send(message: String) = {
    channel.write(message)
  }
}
