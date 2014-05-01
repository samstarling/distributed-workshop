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