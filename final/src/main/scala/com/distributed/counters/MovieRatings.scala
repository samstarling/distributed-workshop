package com.distributed.counters

import java.util
import com.zink.queue.ConnectionFactory
import chrisloy.json._

class MovieRatings(nodeId: String) {
  val ratingsCounters = collection.mutable.Map[String, PNCounter]()
  val downloadCounters = collection.mutable.Map[String, GCounter]()
  val nodeIds = new util.HashSet[String]
  nodeIds.add(nodeId)

  def thumbsUp(movie: String) = {
    ratingsCounters.get(movie) match {
      case Some(counter) => counter.inc(movie, 1)
      case None => {
        val incCounter = new GCounter(nodeIds)
        val decCounter = new GCounter(nodeIds)
        val thumbsCounter = new PNCounter(nodeIds, incCounter, decCounter);
        thumbsCounter.inc(movie, 1)
        ratingsCounters.put(movie, thumbsCounter)
      }
    }
  }

  def thumbsDown(movie: String) = {
    ratingsCounters.get(movie) match {
      case Some(counter) => counter.dec(movie, 1)
      case None => {
        val incCounter = new GCounter(nodeIds)
        val decCounter = new GCounter(nodeIds)
        val thumbsCounter = new PNCounter(nodeIds, incCounter, decCounter);
        thumbsCounter.dec(movie, 1)
        ratingsCounters.put(movie, thumbsCounter)
      }
    }
  }

  def rating(movie: String): Int = {
    ratingsCounters.get(movie) match {
      case Some(counter) => counter.value.toInt
      case None => 0
    }
  }

  def incDownloads(movie: String) = {
    downloadCounters.get(movie) match {
      case Some(counter) => counter.inc(movie)
      case None => {
        val movies = new util.HashSet[String]
        movies.add(movie)
        val downloadsCounter = new GCounter(nodeIds)
        downloadsCounter.inc(movie)
        downloadCounters.put(movie, downloadsCounter)
      }
    }
  }

  def getDownloads(movie: String): Int = {
    downloadCounters.get(movie) match {
      case Some(counter) => counter.value.toInt
      case None => 0
    }
  }

  def mergeDownloadCounters(counters1: collection.mutable.Map[String, GCounter], counters2: collection.mutable.Map[String, GCounter]) {
    val mergedCounters = collection.mutable.Map[String, GCounter]()
    mergedCounters ++ counters2

    for((movie, counter) <- counters1) {
      if (counters2.contains(movie)) {
        mergedCounters.put (movie, counter merge counters2.get(movie).get)
      }
      else {
        mergedCounters.put (movie, counter)
      }
    }
    mergedCounters
  }

  def mergeRatingsCounters(counters1: collection.mutable.Map[String, PNCounter], counters2: collection.mutable.Map[String, PNCounter]) {
    val mergedCounters = collection.mutable.Map[String, PNCounter]()
    mergedCounters ++ counters2

    for((movie, counter) <- counters1) {
      if (counters2.contains(movie)) {
        mergedCounters.put (movie, counter merge counters2.get(movie).get)
      }
      else {
        mergedCounters.put (movie, counter)
      }
    }
    mergedCounters
  }
}

object CounterListener {
  val con = ConnectionFactory.connect()
  val wq = con.subscribe("a")
  val countersJson = Json.parse(wq.read.toString)
}
