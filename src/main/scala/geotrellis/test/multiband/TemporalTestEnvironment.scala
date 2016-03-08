package geotrellis.test.multiband

import geotrellis.proj4.WebMercator
import geotrellis.raster.{MultiBandTile, Tile}
import geotrellis.spark._
import geotrellis.spark.ingest._
import geotrellis.spark.io.{LayerReader, Writer}
import geotrellis.spark.tiling.ZoomedLayoutScheme
import geotrellis.test.TestEnvironment
import geotrellis.test._
import geotrellis.util.SparkSupport
import org.apache.spark.rdd.RDD

import scala.util.Random

trait TemporalTestEnvironment extends TestEnvironment { self: SparkSupport =>
  type I = TemporalProjectedExtent
  type K = SpaceTimeKey
  type V = MultiBandTile
  type M = RasterMetaData

  def loadTiles: RDD[(I, V)]

  val writer: Writer[LayerId, RDD[(K, V)] with Metadata[M]]
  val reader: LayerReader[LayerId, RDD[(K, V)] with Metadata[M]]

  def ingest(layer: String): Unit = {
    conf.set("io.map.index.interval", "1")

    logger.info(s"ingesting tiles into accumulo (${layer})...")
    MultiBandIngest[I, K](loadTiles, WebMercator, ZoomedLayoutScheme(WebMercator), pyramid = true) { case (rdd, z) =>
      if (z == 25) {
        //if (rdd.filter(!_._2.isNoDataTile).count != 58) {
        //  logger.error(s"Incorrect ingest ${layer}")
        //  throw new Exception(s"Incorrect ingest ${layer}")
        //}
      }

      writer.write(LayerId(layer, z), rdd)
    }
  }

  def read(layerId: LayerId): RDD[(K, V)] with Metadata[M] = {
    logger.info(s"reading ${layerId}...")
    reader.read(layerId)
  }

  def combine(layerId: LayerId): K = {
    logger.info(s"combineLayer ${layerId}...")
    val rdd = read(layerId)
    val crdd =
      (rdd union rdd)
        .map { case (k, v) => (k, (k, v)) }
        .combineByKey(createTiles[K, V], mergeTiles1[K, V], mergeTiles2[K, V])
        .map { case (key: K, seq: Seq[(K, V)]) =>
          val tiles = seq.map(_._2)
          key -> tiles(0).band(0).combine(tiles(1).band(0))(_ + _)
        }

    crdd.cache()

    val keys = crdd.keys.collect()
    val key  = keys(Random.nextInt(keys.length))

    val ctile = crdd.lookup(key).map(_.toArray).head
    val tile  = rdd.lookup(key).map(t => t.band(0).combine(t.band(0))(_ + _).toArray).head

    if(!ctile.sameElements(tile)) {
      logger.error(s"Incorrect combine layers ${layerId}")
      throw new Exception(s"Incorrect combine layers ${layerId}")
    }

    key
  }
}