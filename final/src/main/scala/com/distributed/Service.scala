import java.net.URL
import java.net.URLConnection
import java.util.UUID
import java.util.Scanner
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import com.zink.cache._

trait MovieService {
  def getPopular: Seq[String]
  def getDetailsById(id: String): String
}

class MovieServiceImpl extends MovieService {
  val base = "https://api.themoviedb.org/3/"
  val key  = "?api_key=89e5511513f926ee8ac9569963afa8f2"
  val idRegex = """"id":(\d+)""".r
  
  def getPopular(): Seq[String] = {
    val pop = "movie/popular"
    val result = httpGet(base + pop + key)
    
    (idRegex findAllIn result).matchData.map(_.group(1).toString).toSeq
  }
  
  def getDetailsById(id: String): String = {
    val movie = "movie/"
    httpGet(base + movie + id + key)
  }
  
  protected def httpGet(uri: String): String = {
    val conn = new URL(uri).openConnection()
    val scr = new Scanner(conn.getInputStream)
    scr.useDelimiter("\\Z")
    scr.next()
  }
}

object MovieServiceRunner extends App {
  val service  = new MovieServiceImpl
  
  println("Popular Movies:")
  
  for (movieId <- service.getPopular) {
    println(s"  * $movieId")
  }
}