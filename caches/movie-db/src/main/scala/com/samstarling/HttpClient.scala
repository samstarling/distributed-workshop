package com.samstarling

import java.net.URL
import java.util.Scanner

object HttpClient {
  def get(uri: String) = {
    val conn = new URL(uri).openConnection()
    val scr = new Scanner(conn.getInputStream())
    scr.useDelimiter("\\Z")
    scr.next()
  }
}
