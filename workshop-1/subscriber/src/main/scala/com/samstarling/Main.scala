package com.samstarling

object Main extends App {

  println("--> Subscriber")

  def consume: Unit = {
    val message = Consumer.read("bbc")

    if (message != "END") {
      val isDaniel = message.toString.contains("Daniel")
      if (isDaniel) Producer.send("ACK", "reply")
      consume
    } else {
      Producer.send("END", "reply")
    }
  }


  while(true) {
    println("Consuming...")
    consume
    Thread.sleep(1000)
  }
}

