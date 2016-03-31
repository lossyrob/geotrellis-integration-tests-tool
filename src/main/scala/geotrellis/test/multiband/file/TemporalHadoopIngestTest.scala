package geotrellis.test.multiband.file

import geotrellis.raster.MultibandTile
import geotrellis.spark._
import geotrellis.spark.io._
import geotrellis.test.FileTest
import geotrellis.test.multiband.load.TemporalHadoopLoad

import org.apache.spark.SparkContext
import com.typesafe.config.{Config => TConfig}

abstract class TemporalHadoopIngestTest(implicit configuration: TConfig) extends FileTest[TemporalProjectedExtent, SpaceTimeKey, MultibandTile](configuration) with TemporalHadoopLoad

object TemporalHadoopIngestTest {
  def apply(implicit configuration: TConfig, _sc: SparkContext) = new TemporalHadoopIngestTest {
    @transient implicit val sc = _sc
  }
}