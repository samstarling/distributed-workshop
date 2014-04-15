package com.samstarling

import java.math.BigInteger

case class HashRing(size: Int = 64000) {
  val ring = new Array[RingNode](size)

  def add(node: RingNode) = {
    val position = node.hash mod(BigInteger.valueOf(size))
    println(s"Added ${node.name} at ${position}")
  }
}
