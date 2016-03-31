package geotrellis.test.singleband.accumulo

import geotrellis.raster.Tile
import geotrellis.spark._
import geotrellis.spark.io._
import geotrellis.test.AccumuloTest
import geotrellis.test.singleband.load.S3Load
import geotrellis.vector.ProjectedExtent
import org.apache.spark.SparkContext
import com.typesafe.config.{Config => TConfig}
import geotrellis.util.S3Support

abstract class S3IngestTest(implicit configuration: TConfig) extends AccumuloTest[ProjectedExtent, SpatialKey, Tile](configuration) with S3Support with S3Load

object S3IngestTest {
  def apply(implicit configuration: TConfig, _sc: SparkContext) = new S3IngestTest {
    @transient implicit val sc = _sc
  }
}