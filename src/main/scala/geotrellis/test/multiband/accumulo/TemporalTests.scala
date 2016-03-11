package geotrellis.test.multiband.accumulo

import geotrellis.spark.SpaceTimeKey
import geotrellis.spark.io._
import geotrellis.spark.io.accumulo._
import geotrellis.spark.io.index.ZCurveKeyIndexMethod
import geotrellis.test.multiband.TemporalTestEnvironment
import geotrellis.util.AccumuloSupport

abstract class TemporalTests extends TemporalTestEnvironment with AccumuloSupport {
  @transient lazy val writer = AccumuloLayerWriter[SpaceTimeKey, V, M](instance, table, ZCurveKeyIndexMethod.byYear)
  @transient lazy val reader = AccumuloLayerReader[SpaceTimeKey, V, M](instance)
  @transient lazy val attributeStore = AccumuloAttributeStore(instance.connector)
}
