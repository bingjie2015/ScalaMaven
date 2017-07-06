
import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithLBFGS}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.{LabeledPoint, LinearRegressionWithSGD}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

import scala.io.Source

/**
  * Created by hadoop on 17/07/06.
  */
//case class Data(att:Int,a:Double,b:Double,c:Double)
object Logical {
  def main(args: Array[String]): Unit = {
    val filepath  = "/home/hadoop/Desktop/test.txt"
    var i =1
    try{
      for (line <- Source.fromFile(filepath,"utf-8").getLines() ){
      //  println(line)
      val part = line.split(",")
       part(1).split("\\s+").foreach(x =>println(x))

      }}catch {
      case e =>println(e)
    }


  }
//  val peopleDF = spark.spark.sparkContext
//    .textFile("/home/hadoop/Desktop/test.txt")
//    .map(_.split("\\s+"))
//    .map(attributes => Data(attributes(0).toInt, attributes(1).trim.toDouble,attributes(2).toDouble,attributes(3).trim.toDouble))
//    .collect().toSeq

//val training = spark.spark.read.format("txt").load("/usr/local/spark/data/mllib/test.txt")
//  val lr = new LogisticRegression()
//    .setMaxIter(10)
//    .setRegParam(0.3)
//    .setElasticNetParam(0.8)
//  val lrModel = lr.fit(training)


  //println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")


    val conf = new SparkConf().setAppName("LOgical").setMaster("local[*]")
    val sc = new SparkContext(conf)
  val document = sc.textFile("/home/hadoop/Desktop/test.txt")
  println(document.count())
  val q = document.map{x =>
    val parts = x.split(",")
    LabeledPoint(parts(0).toInt, Vectors.dense(parts(1).split("\\s+").map(_.toDouble)))
  }.cache()
println(q.count() )

  val numIterations = 100
  val stepSize = 0.00000001
  val model1 = LinearRegressionWithSGD.train(q, numIterations, stepSize)
  val valuesAndPreds = q.map { point =>
    val prediction = model1.predict(point.features)
    (point.label, prediction)
  }
  val MSE = valuesAndPreds.map{ case(v, p) => math.pow((v - p), 2) }.mean()
  println("training Mean Squared Error = " + MSE)




//  val splits = q.randomSplit(Array(0.6, 0.4))
//  println(splits)
//  val training = splits(0)
//  val test = splits(1)
// // println(test.collect().toString)
//  //test.first().toString()
//  //test.foreach(x =>println(x))
//
//
//  val model = new LogisticRegressionWithLBFGS()
//    .run(training)
//  // Compute raw scores on the test set.
//  val predictionAndLabels = test.map { case LabeledPoint(label, features) =>
//    val prediction = model.predict(features)
//    (prediction, label)
//  }
//
//  // Get evaluation metrics.
//  val metrics = new MulticlassMetrics(predictionAndLabels)
//  val accuracy = metrics.accuracy
//  println(s"Accuracy = $accuracy")

  // Save and load model
  //model.save(sc, "target/tmp/scalaLogisticRegressionWithLBFGSModel")
//  val sameModel = LogisticRegressionModel.load(sc,
//    "target/tmp/scalaLogisticRegressionWithLBFGSModel")
//  val numIterations = 100
//  val stepSize = 0.00000001
 // val model = LinearRegressionWithSGD.train(q, numIterations, stepSize)

 //
}
