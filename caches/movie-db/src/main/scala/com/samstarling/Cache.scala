package com.samstarling

import com.zink.cache.CacheFactory

object Cache {
  val cache = CacheFactory.connect()

  def getOrPopulate(key: String)(fn: String): String = {
    if(cache.get(key) == null) {
      cache.set(key, fn)
    }
    cache.get(key).toString
  }
}
