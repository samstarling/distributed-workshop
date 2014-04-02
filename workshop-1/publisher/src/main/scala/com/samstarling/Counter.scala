package com.samstarling

object Counter {
  def run = {
    consume(0)
  }

  def consume(count: Int): Unit = {
    val message = Consumer.read("reply")
    if (message != "END") {
      consume(count + 1)
    } else {
      println(s"Counted: ${count}")
    }
  }
}
