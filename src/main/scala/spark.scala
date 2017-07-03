import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

/**
  * Created by hadoop on 17/06/11.
  */
class spark {

}
object spark {

  val spark = SparkSession
    .builder().
    master("local")
    .appName("StructuredNetworkWordCount")
    .getOrCreate()
  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.WARN)
}
