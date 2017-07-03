import org.apache.spark.ml.feature.OneHotEncoder

import scala.collection.JavaConverters._

/**
  * Created by hadoop on 17/06/11.
  */
object IndexToString {
  def main(args: Array[String]): Unit = {

  }
  import org.apache.spark.ml.attribute.Attribute
  import org.apache.spark.ml.feature.{IndexToString, StringIndexer}

  val df = spark.spark.createDataFrame(Seq(
    (0, "a"),
    (1, "b"),
    (2, "c"),
    (3, "a"),
    (4, "a"),
    (5, "c")
  )).toDF("id", "category")

  val indexer = new StringIndexer()
    .setInputCol("category")
    .setOutputCol("categoryIndex")
    .fit(df)
  val indexed = indexer.transform(df)

  println(s"Transformed string column '${indexer.getInputCol}' " +
    s"to indexed column '${indexer.getOutputCol}'")
  indexed.show()

  val inputColSchema = indexed.schema(indexer.getOutputCol)
  println(s"StringIndexer will store labels in output column metadata: " +
    s"${Attribute.fromStructField(inputColSchema).toString}\n")

  val converter = new IndexToString()
    .setInputCol("categoryIndex")
    .setOutputCol("originalCategory")

  val converted = converter.transform(indexed)

  println(s"Transformed indexed column '${converter.getInputCol}' back to original string " +
    s"column '${converter.getOutputCol}' using labels in metadata")
  converted.select("id", "categoryIndex", "originalCategory").show()


  val df1 = spark.spark.createDataFrame(Seq(
    (0, "a"),
    (1, "b"),
    (2, "c"),
    (3, "a"),
    (4, "a"),
    (5, "c")
  )).toDF("id", "category")

  val indexer1 = new StringIndexer()
    .setInputCol("category")
    .setOutputCol("categoryIndex")
    .fit(df1)
  val indexed1 = indexer1.transform(df)

  val encoder = new OneHotEncoder()
    .setInputCol("categoryIndex")
    .setOutputCol("categoryVec")

  val encoded = encoder.transform(indexed1)
  encoded.show()
}
