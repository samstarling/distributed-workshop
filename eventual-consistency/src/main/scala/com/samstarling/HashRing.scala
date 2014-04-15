package com.samstarling

case class HashRing(size: BigInt = 64000) {
  val ring = new Array[RingNode](size.intValue)

  def add(node: RingNode) = {
    node.hashes.foreach(hash => {
      val position = hash.mod(size)
      ring(position.intValue) = node
    })
  }

  def findNode(key: String): RingNode = {
    def next(current: BigInt): RingNode = {
      Option(ring(current.intValue)) match {
        case Some(node) => node
        case None => next((current + 1) % size.intValue)
      }
    }
    next(Hasher.sha1(key) % size.intValue)
  }
}
