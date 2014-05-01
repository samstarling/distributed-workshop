package com.distributed.simulator

import com.distributed.{Logger, Helpers, MovieServiceImpl}
import scala.concurrent.{ExecutionContext, Await, future, Future}
import scala.concurrent.duration._


object ClientSimulator {
  import ExecutionContext.Implicits.global

  def run(numberOfClients: Integer) = {
    val futures = 1 to 5 map { id =>
      future { Client(s"client$id").run() }
    }
    Await.result(Future.sequence(futures), 60.seconds)
  }
}





