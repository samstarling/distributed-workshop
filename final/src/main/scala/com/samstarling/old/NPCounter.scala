package com.samstarling.old

import scala.collection.mutable

case class NPCounter(inc: mutable.Map[String, Int] = mutable.Map.empty, dec: mutable.Map[String, Int] = mutable.Map.empty) {

  def inc(node: String, number: Int) {
    inc.get(node) match {
      case Some(value) => inc.put(node, value + number)
      case None => inc.put(node, number)
    }
  }

  def dec(node: String, number: Int) {
    inc.get(node) match {
      case Some(value) => dec.put(node, value + number)
      case None => dec.put(node, number)
    }
  }

  def value = {
    inc.foldLeft(0)(_+_._2) - dec.foldLeft(0)(_+_._2)
  }

  def getForNode(map: mutable.Map[String, Int], node: String) = {
    map.get(node) getOrElse 0
  }

  def merge(that: NPCounter): NPCounter = {
    val incTuples =
      for (node <- this.inc.keys.toSet ++ that.inc.keys.toSet)
      yield node -> math.max(this.getForNode(inc, node), that.getForNode(inc, node))

    val decTuples =
      for (node <- this.dec.keys.toSet ++ that.dec.keys.toSet)
      yield node -> math.max(this.getForNode(dec, node), that.getForNode(dec, node))

    val incResult = mutable.Map[String, Int]()
    incTuples.foreach(item => incResult.put(item._1, item._2))

    val decResult = mutable.Map[String, Int]()
    decTuples.foreach(item => decResult.put(item._1, item._2))

    NPCounter(incResult, decResult)
  }
}
