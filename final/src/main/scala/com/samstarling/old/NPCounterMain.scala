package com.samstarling.old

object NPCounterMain {
  val x = new NPCounter()
  val y = new NPCounter()
  val z = new NPCounter()

  x.inc("a", 10)
  x.inc("b", 20)
  y.inc("b", 10)
  y.inc("c", 5)
  z.inc("a", 100)
  z.dec("b", 5000)

  val merged = (x merge y merge z)
  println(s"Total: ${merged.value} - ${merged.inc}, ${merged.dec}")
}
