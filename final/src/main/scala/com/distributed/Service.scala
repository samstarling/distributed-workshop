package com.distributed

import java.net.URL
import java.util.Scanner
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import com.zink.cache._
import chrisloy.json._
import com.distributed.counters._

case class Movie(id: String, title: String) {}
  
object Movie {
  def fromJson(json: JsonValue): Option[Movie] = json match {
    case JsonObject(fields) => Some(Movie(
      fields("id").asInstanceOf[JsonNumber].value.toInt.toString, 
      fields("title").asInstanceOf[JsonString].value))
    case _ => None
  }
}

trait MovieService {
  def getPopular: Seq[String]
  def getDetailsById(id: String): Option[Movie]
  def rateMovieUp(id: String): Unit
  def rateMovieDown(id: String): Unit
}

class MovieServiceImpl extends MovieService {
  val base = "https://api.themoviedb.org/3/"
  val key  = "?api_key=89e5511513f926ee8ac9569963afa8f2"
  val idRegex = """"id":(\d+)""".r
  
  def getPopular: Seq[String] = {
    val pop = "movie/popular"
    val result = httpGet(base + pop + key)
    
    (idRegex findAllIn result).matchData.map(_.group(1).toString).toSeq
  }
  
  def getDetailsById(id: String): Option[Movie] = {
    MovieRatings.incDownloads(id)
    Movie.fromJson(Json parse httpGet(base + "movie/" + id + key))
  }

  def rateMovieUp(id: String) = {
    MovieRatings.thumbsUp(id)
  }

  def rateMovieDown(id: String) = {
    MovieRatings.thumbsDown(id)
  }
  
  protected def httpGet(uri: String): String = {
    val conn = new URL(uri).openConnection()
    val scr = new Scanner(conn.getInputStream)
    scr.useDelimiter("\\Z")
    scr.next()
  }
}

trait Caching extends MovieService { self: MovieService =>
  val cache = CacheFactory.connect()
  
  def getPopular: Seq[String] = {
    val cacheResult = cache.get("popular")
    
    if (cacheResult != null) cacheResult.toString.split(",")
    else {
      val result = getPopular
      cache.set("popular", result.mkString(","))
      cache.expire("popular", 60000) // one minute TTL
      result
    }
  }
}

object MovieServiceRunner extends App {
  val service  = new MovieServiceImpl
  val popular = service.getPopular.take(5)
  
  println("Top 5 Movies:")
  
  for (movieId <- popular; movie <- service.getDetailsById(movieId)) {
    println(s"  * ${movie.title}")
  }
}