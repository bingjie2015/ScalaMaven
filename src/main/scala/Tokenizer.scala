/**
  * Created by hadoop on 17/06/11.
  *  高级模式 可以 自己配置  用于分割字符
  */
object Tokenizer {
  import org.apache.spark.ml.feature.{RegexTokenizer, Tokenizer}

  def main(args: Array[String]): Unit = {

  }
  val sentenceDataFrame = spark.spark.createDataFrame(Seq(
    (0, "Hi I heard about Spark"),
    (1, "I wish Java could use case classes"),
    (2, "Logistic,  regression,models,are,neat")
  )).toDF("label", "sentence")

  val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
  // 高级模式 可以 自己配置  用于分割字符
  val regexTokenizer = new RegexTokenizer()
    .setInputCol("sentence")
    .setOutputCol("words")
    .setPattern("\\W+") // alternatively .setPattern("\\w+").setGaps(false)

  val tokenized = tokenizer.transform(sentenceDataFrame)
  tokenized.select("words", "label").take(3).foreach(println)
  val regexTokenized = regexTokenizer.transform(sentenceDataFrame)
  regexTokenized.select("words", "label").take(3).foreach(println)
}
