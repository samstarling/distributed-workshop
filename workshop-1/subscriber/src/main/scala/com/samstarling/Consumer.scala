package com.samstarling

import com.zink.queue.ConnectionFactory

object Consumer {
   val connection = ConnectionFactory.connect()
   val channel = connection.subscribe("BBC")

   def consume = channel.read
 }
