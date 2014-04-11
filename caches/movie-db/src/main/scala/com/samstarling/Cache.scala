package com.samstarling

import com.zink.cache.CacheFactory
import java.util.concurrent.atomic.AtomicInteger
import scala.util.Random
import java.text.NumberFormat
import java.util.Locale

object Cache {
  val cache = CacheFactory.connect()
  val hits: AtomicInteger = new AtomicInteger()
  val misses: AtomicInteger = new AtomicInteger()

  def getOrPopulate(key: String)(fn: => String): String = {
    val result = cache.get(key)
    if(result == null) {
      val response = fn
      cache.set(key, response)
      cache.expire(key, Random.nextInt(1000))
      misses.getAndIncrement
      response
    } else {
      hits.getAndIncrement
      result.toString
    }
  }

  def friendlyHits = NumberFormat.getNumberInstance(Locale.US).format(hits)
  def friendlyMisses = NumberFormat.getNumberInstance(Locale.US).format(misses)

  override def toString = {
    s"${Console.GREEN}✔ ${friendlyHits}${Console.RESET}\t${Console.RED}✘ ${friendlyMisses}${Console.RESET}"
  }
}