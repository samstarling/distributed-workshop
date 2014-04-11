package com.samstarling

case class DanielWorker(name: String) extends Worker {
  var count = 0

  def run(): Unit = {
    val message = Consumer.read("bbc")
    count = count + 1
    println(s"[${name}] rx: ${count}")

    if (message != "END") {
      val isDaniel = message.toString.contains("Daniel")
      if (isDaniel) Producer.send("daniel", "reply")
      run()
    } else {
      Producer.send("END", "reply")
    }
  }
}
