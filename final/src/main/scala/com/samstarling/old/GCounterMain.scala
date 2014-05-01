package com.samstarling.old

object GCounterMain {
  val x = new GCounter()
  val y = new GCounter()
  val z = new GCounter()
  x.inc("a", 10)
  x.inc("b", 20)
  y.inc("b", 10)
  y.inc("c", 5)
  z.inc("a", 100)
  println((x merge y merge z).values)
}
