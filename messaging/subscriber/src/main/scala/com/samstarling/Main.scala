package com.samstarling

import scala.concurrent._
import ExecutionContext.Implicits.global

object Main extends App {

  val workers: List[Worker] = List(
    DanielWorker("daniel"),
    NSPlayerWorker("nsplayer")
  )

  val fs = workers map { w =>
    future { w.run() }
  }

  Thread.sleep(120 * 1000)
}

