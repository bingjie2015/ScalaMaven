import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by hadoop on 17/06/13.
  */
object test {
  def main(args: Array[String]): Unit = {

  }
  import org.apache.spark.mllib.linalg.Vectors
  import org.apache.spark.mllib.stat.{MultivariateStatisticalSummary, Statistics}
  val conf = new SparkConf().setAppName("Simple Application").setMaster("local[*]")
  val sc = new SparkContext(conf)
  val observations = sc.parallelize(
    Seq(
      Vectors.dense(1.0, 10.0, 100.0),
      Vectors.dense(2.0, 20.0, 200.0),
      Vectors.dense(3.0, 30.0, 300.0)
    )
  )
  val aa = Seq(
    Vectors.dense(1.0, 10.0, 100.0),
    Vectors.dense(2.0, 20.0, 200.0),
    Vectors.dense(3.0, 30.0, 300.0)
  )
  // Compute column summary statistics.
  val summary: MultivariateStatisticalSummary = Statistics.colStats(observations)
  println(summary.mean)  // a dense vector containing the mean value for each column
  println(summary.variance)  // column-wise variance
  println(summary.numNonzeros)  // number of nonzeros in each column
 // val Array(tring, aa)  =(Array(0.7,0.3));
 // tring.
  //observations.aggregate(observations);
}
