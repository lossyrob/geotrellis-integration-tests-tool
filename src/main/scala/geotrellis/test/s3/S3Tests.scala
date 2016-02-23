package geotrellis.test.s3

import geotrellis.spark.io.s3.{S3LayerReader, S3LayerWriter}
import geotrellis.spark.io.index.ZCurveKeyIndexMethod
import geotrellis.spark.io.avro.codecs._
import geotrellis.spark.io.json._
import geotrellis.test.TestEnvironment
import geotrellis.util.{S3Support, SparkSupport}

trait S3Tests extends SparkSupport with TestEnvironment with S3Support with Serializable {
  @transient lazy val writer = S3LayerWriter[K, V, M](s3Bucket, s3Preifx, ZCurveKeyIndexMethod)
  @transient lazy val reader = S3LayerReader[K, V, M](s3Bucket, s3Preifx)
}