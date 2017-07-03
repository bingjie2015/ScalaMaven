/**
  * Created by hadoop on 17/06/11.
  */
import org.apache.spark.ml.feature.{CountVectorizer, CountVectorizerModel}

object CountVectorizer {
  def main(args: Array[String]): Unit = {

  }
  val df = spark.spark.createDataFrame(Seq(
    (0, Array("a", "b", "c","D","a","a")),
    (1, Array("a", "b", "D", "b", "a","f"))
  )).toDF("id", "words")

  // fit a CountVectorizerModel from the corpus
  val cvModel: CountVectorizerModel = new CountVectorizer()
    .setInputCol("words")
    .setOutputCol("features")
    .setVocabSize(10)
    //setMinDF 最少应该出现的次数在所有的文档中 , a b d 出现在两个文档中
    .setMinDF(2)
    .fit(df)

  // alternatively, define CountVectorizerModel with a-priori vocabulary
  val cvm = new CountVectorizerModel(Array("a", "b", "c"))
    .setInputCol("words")
    .setOutputCol("features")

  cvModel.transform(df).show(false)
}
