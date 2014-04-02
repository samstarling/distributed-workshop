package com.samstarling


object Main extends App {
  def consume(count: Int): Unit = {
    val message = Consumer.consume

    if (message != "END") {
      val isDaniel = message.toString.contains("Daniel")
      val newCount = if (isDaniel) (count + 1) else count
      consume(newCount)
    } else {
      print(s"Counted ${count}")
    }
  }

  consume(0)
}

