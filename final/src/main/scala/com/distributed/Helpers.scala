package com.distributed

object Helpers {
  def maybe(chance: Double)(success: => Any)(failure: => Any) = {
    if(scala.util.Random.nextFloat() < chance) {
      success
    } else {
      failure
    }
  }
}
