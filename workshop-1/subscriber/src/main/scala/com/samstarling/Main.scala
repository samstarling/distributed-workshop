package com.samstarling

import scala.concurrent._
import ExecutionContext.Implicits.global

object Main extends App {
  val fs = 1 to 3 map { x =>
    future {
      val workerName = s"worker${x}"
      println(s"--> Starting ${workerName}")
      Worker(workerName).run()
    }
  }

  Thread.sleep(120 * 1000)
}

case class Worker(name: String) {
  var count = 0

  def run(): Unit = {
    val message = Consumer.read("bbc")
    count = count + 1
    println(s"[${name}] rx: ${count}")

    if (message != "END") {
      val isDaniel = message.toString.contains("Daniel")
      if (isDaniel) Producer.send("ACK", "reply")
      run()
    } else {
      Producer.send("END", "reply")
    }
  }
}