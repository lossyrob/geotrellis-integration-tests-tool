package geotrellis.test.singleband.hbase

import geotrellis.config.Dataset
import geotrellis.raster.Tile
import geotrellis.spark._
import geotrellis.spark.io._
import geotrellis.test.HBaseTest
import geotrellis.test.singleband.load.S3Load
import geotrellis.util.S3Support
import geotrellis.vector.ProjectedExtent

import org.apache.spark.SparkContext

abstract class S3IngestTest(dataset: Dataset) extends HBaseTest[ProjectedExtent, SpatialKey, Tile](dataset) with S3Support with S3Load

object S3IngestTest {
  def apply(implicit dataset: Dataset, _sc: SparkContext) = new S3IngestTest(dataset) {
    @transient implicit val sc = _sc
  }
}
