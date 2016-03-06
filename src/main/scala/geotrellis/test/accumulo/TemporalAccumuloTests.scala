package geotrellis.test.accumulo

import geotrellis.spark.io.accumulo._
import geotrellis.spark.io.index.ZCurveKeyIndexMethod
import geotrellis.test.TemporalTestEnvironment
import geotrellis.util.{AccumuloSupport, SparkSupport}

trait TemporalAccumuloTests extends SparkSupport with TemporalTestEnvironment with AccumuloSupport with Serializable {
  @transient lazy val writer = AccumuloLayerWriter[K, V, M](instance, table, ZCurveKeyIndexMethod.byYear)
  @transient lazy val reader = AccumuloLayerReader[K, V, M](instance)
}
