package com.samstarling

case class RingNode(name: String) {
  val hash = Hasher.sha1(name)
  val map = Map[String, Object]()
}
