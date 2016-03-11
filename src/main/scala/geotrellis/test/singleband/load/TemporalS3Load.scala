package geotrellis.test.singleband.load

import geotrellis.spark.TemporalProjectedExtent
import geotrellis.spark.etl.s3.TemporalGeoTiffS3Input
import geotrellis.test.singleband.TemporalTestEnvironment

import org.apache.spark.rdd.RDD

trait TemporalS3Load { self: TemporalTestEnvironment =>
  val layerName: String = "s3Ingest"
  val zoom: Int = 8

  def loadTiles: RDD[(TemporalProjectedExtent, V)] = {
    logger.info("loading tiles from s3...")
    val s3Input = new TemporalGeoTiffS3Input()
    s3Input(s3Params)
  }
}
