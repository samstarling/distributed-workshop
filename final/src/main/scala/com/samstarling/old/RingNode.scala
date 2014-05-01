package com.samstarling.old

case class RingNode(name: String) {
  val hashes = List(
    Hasher.sha1(s"${name}-aaa"),
    Hasher.sha1(s"${name}-bbb"),
    Hasher.sha1(s"${name}-ccc"),
    Hasher.sha1(s"${name}-ddd"),
    Hasher.sha1(s"${name}-eee")
  )

  val map = Map[String, Object]()
}
