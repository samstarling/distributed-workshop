package com.samstarling

object GCounterMain {
  val x = new GCounter()
  val y = new GCounter()
  x.inc("a", 10)
  x.inc("b", 20)
  y.inc("b", 10)
  println((x merge y).values)
}
