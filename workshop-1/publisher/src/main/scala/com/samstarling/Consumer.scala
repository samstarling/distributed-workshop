package com.samstarling

import com.zink.queue.ConnectionFactory

object Consumer {
  val connection = ConnectionFactory.connect()

  def read(channel: String = "BBC") = {
    connection.subscribe(channel).read
  }
}

