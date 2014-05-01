package com.distributed.counters

import java.util
import com.distributed.counters.{GCounter, PNCounter}

class MovieRatings(nodeId: String) {
  var ratingsCounters = collection.mutable.Map[String, PNCounter]()
  var downloadCounters = collection.mutable.Map[String, GCounter]()
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
}
