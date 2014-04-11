package com.samstarling

object Counter {
  
  def run = {
    consume(0, 0)
  }

  def consume(daniels: Int, nsPlayers: Int): Unit = {
    val message = Consumer.read("reply")
    if (message != "END") {
      message match {
        case "daniel" => consume(daniels + 1, nsPlayers)
        case "nsplayer" => consume(daniels, nsPlayers + 1)
        case _ => consume(daniels, nsPlayers)
      }
    } else {
      println(s"Counted: ${daniels} / ${nsPlayers}")
    }
  }
}
