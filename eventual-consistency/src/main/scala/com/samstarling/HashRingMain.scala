package com.samstarling

object HashRingMain {
  val hashRing = HashRing()
  hashRing.add(RingNode("Node1"))
  hashRing.add(RingNode("Node2"))
  hashRing.add(RingNode("Node3"))

  hashRing.findNode("foo")
  hashRing.findNode("bar")
  hashRing.findNode("baz")

  for((x,i) <- hashRing.ring.view.zipWithIndex) {
    if(x != null) println("String #" + i + " is " + x)
  }
}
