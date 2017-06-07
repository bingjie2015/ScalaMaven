/**
  * Created by hadoop on 17/05/31.
  */
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}
object StructedNetwork {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder().
      master("local")
      .appName("StructuredNetworkWordCount")
      .getOrCreate()
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.WARN)
    import spark.implicits._
    val lines = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9994)
      .load()
    // line 代表DataFrame 无限接受 的表  每一行column名 是 "value"  row 是读取的数据
    // Split the lines into words
    val words = lines.as[String].flatMap(_.split(" "))

    // Generate running word count
    val wordCounts = words.groupBy("Value").count()

    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination();
    println("Suceess");

  }
}

