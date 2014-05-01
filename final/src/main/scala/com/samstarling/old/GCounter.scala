package com.samstarling.old

import scala.collection.mutable

case class GCounter(values: mutable.Map[String, Int] = mutable.Map.empty) {

  def inc(node: String, number: Int) {
    values.get(node) match {
      case Some(value) => values.put(node, value + number)
      case None => values.put(node, number)
    }
  }

  def value = {
    values.foldLeft(0)(_+_._2)
  }

  def getForNode(node: String) = {
    values.get(node) getOrElse 0
  }

  def merge(that: GCounter): GCounter = {
    val tuples =
      for (node <- this.values.keys.toSet ++ that.values.keys.toSet)
      yield node -> math.max(this.getForNode(node), that.getForNode(node))

    val result = mutable.Map[String, Int]()
    tuples.foreach(item => result.put(item._1, item._2))

    GCounter(result)
  }
}
