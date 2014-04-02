package com.samstarling

import com.zink.queue.ConnectionFactory

object Producer {
  val connection = ConnectionFactory.connect()

  def send(message: String, channel: String) = {
    connection.publish(channel).write(message)
  }
}
