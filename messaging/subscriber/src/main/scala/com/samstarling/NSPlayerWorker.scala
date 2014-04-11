package com.samstarling

case class NSPlayerWorker(name: String) extends Worker {
  var count = 0

  def run(): Unit = {
    val message = Consumer.read("bbc")
    count = count + 1
    println(s"[${name}] rx: ${count}")

    if (message != "END") {
      val isDaniel = message.toString.contains("NSPlayer")
      if (isDaniel) Producer.send("nsplayer", "reply")
      run()
    } else {
      Producer.send("END", "reply")
    }
  }
}
