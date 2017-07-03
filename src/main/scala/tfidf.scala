/**
  * Created by hadoop on 17/06/09.
  */
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.sql.SparkSession
object tfidf {

  def main(args: Array[String]): Unit = {

  }
  val spark = SparkSession
    .builder().
    master("local")
    .appName("StructuredNetworkWordCount")
    .getOrCreate()
  val rootLogger = Logger.getRootLogger()
  rootLogger.setLevel(Level.WARN)
  val sentenceData = spark.createDataFrame(Seq(
    (0, "Hi In my sorked"),
    (0, "I "),
    (1, "Logistic token golf basketball tea computer"),
    (1,"I"),
    (1,"You")
  )).toDF("label", "sentence")

  val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
  val wordsData = tokenizer.transform(sentenceData)
  val hashingTF = new HashingTF()
    .setInputCol("words").setOutputCol("rawFeatures").setNumFeatures(10)
  val featurizedData = hashingTF.transform(wordsData)
  //得到 对应的词数
 // featurizedData.select("rawFeatures").collect().foreach(println)
  // alternatively, CountVectorizer can also be used to get term frequency vectors

  val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
  val idfModel = idf.fit(featurizedData)
  val rescaledData = idfModel.transform(featurizedData)
  rescaledData.select("features", "label").take(5).foreach(println)
  //rescaledData.select("features", "label").take(5).foreach(println) 可以取规定个数的值而不是全部
  import org.apache.spark.ml.feature.Word2Vec
  import org.apache.spark.ml.linalg.Vector
  import org.apache.spark.sql.Row

  // Input data: Each row is a bag of words from a sentence or document.
  val documentDF = spark.createDataFrame(Seq(
    "Hi I heard about Spark".split(" "),
    "I wish Java could use case classes".split(" "),
    "Logistic regression models are neat".split(" ")
  ).map(Tuple1.apply)).toDF("text")

  // Learn a mapping from words to Vectors.
  val word2Vec = new Word2Vec()
    .setInputCol("text")
    .setOutputCol("result")
    .setVectorSize(3)
    .setMinCount(0)
  val model = word2Vec.fit(documentDF)

  val result = model.transform(documentDF)
  result.collect().foreach { case Row(text: Seq[_], features: Vector) =>
    println(s"Text: [${text.mkString(", ")}] => \nVector: $features\n") }

}
