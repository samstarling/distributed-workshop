package com.distributed

object Helpers {
  def maybe(chance: Double)(block: => Any) = {
    if(scala.util.Random.nextFloat() < chance) {
      block
    }
  }
}
