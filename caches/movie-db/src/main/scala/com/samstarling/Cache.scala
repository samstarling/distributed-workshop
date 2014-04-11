package com.samstarling

import com.zink.cache.CacheFactory

object Cache {
  val cache = CacheFactory.connect()

  var hits = 0
  var misses = 0

  def getOrPopulate(key: String)(fn: String): String = {
    if(cache.get(key) == null) {
      cache.set(key, fn)
      misses = misses + 1
    } else {
      hits = hits + 1
    }
    cache.get(key).toString
  }

  override def toString = {
    s"Hits: ${hits}, misses: ${misses}"
  }
}
