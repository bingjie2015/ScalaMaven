import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.graphx.GraphLoader
/**
  * Created by hadoop on 17/06/21.
  */
object Graf {
  def main(args: Array[String]): Unit = {

  }
  import org.apache.spark.graphx.GraphLoader
  val conf = new SparkConf().setAppName("Simple Application").setMaster("local[*]")
  val sc = new SparkContext(conf)

  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.WARN)
  // Load the edges as a graph
  val graph = GraphLoader.edgeListFile(sc, "/usr/local/spark/data/graphx/followers.txt")
  // Run PageRank
  val ranks = graph.pageRank(0.0001).vertices
  // Join the ranks with the usernames
  val users = sc.textFile("/usr/local/spark/data/graphx/users.txt").map { line =>
    val fields = line.split(",")
    (fields(0).toLong, fields(1))
  }
  val ranksByUsername = users.join(ranks).map {
    case (id, (username, rank)) => (username, rank)
  }
  // Print the result
  println(ranksByUsername.collect().mkString("\n"))
}
