import Logical.q
import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

/**
  * Created by hadoop on 17/07/06.
  */
object LogicalRegrassion {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("LOgical").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val rootLogger = Logger.getRootLogger()
    rootLogger.setLevel(Level.WARN)
    //  val document = sc.textFile("hdfs://163.143.92.188:9000/Data/test.txt")
    var nu=0.987
    //y = 0 pr_mean 1 rp_mean 2 ratio_bm
    var y =0
    val document = sc.textFile("file:///home/hadoop/Desktop/test.txt").randomSplit(Array(nu,1-nu))
    var i =0
    val q = document(0).map { x =>
      val parts = x.split(",")
      require(i<20)
      var pp = parts(1).trim.split("\\s+")
      //for all attributes
      LabeledPoint(parts(0).trim.toInt, Vectors.dense(pp.map(_.toDouble)))
      // for sigle attribute  take(y) 2 3
      //LabeledPoint(parts(0).trim.toInt, Vectors.dense(pp.take(y).map(_.toDouble)))
    }.cache()

    val splits = q.randomSplit(Array(0.7, 0.3))
    println(q.count())
    val training = splits(0)
    val test = splits(1)
    //println(test.collect().toString)
    //test.first().toString()
    //test.foreach(x =>println(x))


    val model = new LogisticRegressionWithLBFGS().setNumClasses(10).run(training)
    // Compute raw scores on the test set.
    val predictionAndLabels = test.map { case LabeledPoint(label, features) =>
      val prediction = model.predict(features)
      (prediction, label)
    }
    //
    //  // Get evaluation metrics.
    val metrics = new MulticlassMetrics(predictionAndLabels)
    val accuracy = metrics.accuracy
    println(s"Accuracy = $accuracy")
  }
}
