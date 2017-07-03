/**
  * Created by hadoop on 17/06/11.
  */
object StopWordsRemover {
  def main(args: Array[String]): Unit = {

  }
  import org.apache.spark.ml.feature.StopWordsRemover

  val remover = new StopWordsRemover()
    .setInputCol("raw")
    .setOutputCol("filtered")

  val dataSet = spark.spark.createDataFrame(Seq(
    (0, Seq("I", "saw", "the", "red", "baloon")),
    (1, Seq("Mary", "had", "a", "little", "lamb"))
  )).toDF("id", "raw")

  remover.transform(dataSet).show()

  import org.apache.spark.ml.feature.NGram

  val wordDataFrame = spark.spark.createDataFrame(Seq(
    (0, Array("Hi", "I", "heard", "about", "Spark")),
    (1, Array("I", "wish", "Java", "could", "use", "case", "classes")),
    (2, Array("Logistic", "regression", "models", "are", "neat"))
  )).toDF("label", "words")
  // 设置3次 所以 3个单词一重复
  val ngram = new NGram().setInputCol("words").setOutputCol("ngrams").setN(3)
  val ngramDataFrame = ngram.transform(wordDataFrame)
  ngramDataFrame.take(3).map(_.getAs[Stream[String]]("ngrams").toList).foreach(println)


  import org.apache.spark.ml.feature.Binarizer

  val data = Array((0, 0.1), (1, 0.8), (2, 0.2))
  val dataFrame = spark.spark.createDataFrame(data).toDF("label", "feature")

  val binarizer: Binarizer = new Binarizer()
    .setInputCol("feature")
    .setOutputCol("binarized_feature")
    .setThreshold(0.1)

  val binarizedDataFrame = binarizer.transform(dataFrame)
  val binarizedFeatures = binarizedDataFrame.select("binarized_feature")
  binarizedFeatures.collect().foreach(println)
}
