package com.distributed

case class Logger(loggerName: String) {
  def debug(msg: String) = println(s"${Console.RED}[DEBUG] ${Console.BLUE}$loggerName ${Console.RESET}$msg")
}
