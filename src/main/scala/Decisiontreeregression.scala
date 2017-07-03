/**
  * Created by hadoop on 17/06/20.
  */
object Decisiontreeregression {
  def main(args: Array[String]): Unit = {

  }
  import org.apache.spark.ml.Pipeline
  import org.apache.spark.ml.evaluation.RegressionEvaluator
  import org.apache.spark.ml.feature.VectorIndexer
  import org.apache.spark.ml.regression.DecisionTreeRegressionModel
  import org.apache.spark.ml.regression.DecisionTreeRegressor

  // Load the data stored in LIBSVM format as a DataFrame.
  val data = spark.spark.read.format("libsvm").load("/usr/local/spark/data/mllib/sample_libsvm_data.txt")
  // Automatically identify categorical features, and index them.
  // Here, we treat features with > 4 distinct values as continuous.
  val featureIndexer = new VectorIndexer()
    .setInputCol("features")
    .setOutputCol("indexedFeatures")
    .setMaxCategories(10)
    .fit(data)
 featureIndexer.transform(data).select("indexedFeatures").show(1,false) 
  val categoricalFeatures: Set[Int] = featureIndexer.categoryMaps.keys.toSet
  println(s"Chose ${categoricalFeatures.size} categorical features: " +
    categoricalFeatures.mkString(", "))

  // Split the data into training and test sets (30% held out for testing).
  val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

  // Train a DecisionTree model.
  val dt = new DecisionTreeRegressor()
    .setLabelCol("label")
    .setFeaturesCol("indexedFeatures")

  // Chain indexer and tree in a Pipeline.
  val pipeline = new Pipeline()
    .setStages(Array(featureIndexer, dt))

  // Train model. This also runs the indexer.
  val model = pipeline.fit(trainingData)

  // Make predictions.
  val predictions = model.transform(testData)

  // Select example rows to display.
  predictions.select("prediction", "label", "features").show(5)

  // Select (prediction, true label) and compute test error.
  val evaluator = new RegressionEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    .setMetricName("rmse")
  val rmse = evaluator.evaluate(predictions)
  println("Root Mean Squared Error (RMSE) on test data = " + rmse)

  val treeModel = model.stages(1).asInstanceOf[DecisionTreeRegressionModel]
  println("Learned regression tree model:\n" + treeModel.toDebugString)
}
