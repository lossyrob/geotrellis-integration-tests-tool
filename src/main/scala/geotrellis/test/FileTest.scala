package geotrellis.test

import geotrellis.config._
import geotrellis.raster.CellGrid
import geotrellis.spark._
import geotrellis.spark.io.avro.AvroRecordCodec
import geotrellis.spark.io.file.{FileAttributeStore, FileLayerReader, FileLayerWriter}
import geotrellis.spark.tiling.TilerKeyMethods
import geotrellis.vector.ProjectedExtent

import spray.json.JsonFormat
import com.typesafe.config.{Config => TConfig}

import scala.reflect.ClassTag

abstract class FileTest[
  I: ClassTag: ? => TilerKeyMethods[I, K]: Component[?, ProjectedExtent],
  K: SpatialComponent: Boundable: AvroRecordCodec: JsonFormat: ClassTag,
  V <: CellGrid: AvroRecordCodec: ClassTag
](dataSet: DataSet) extends TestEnvironment[I, K, V](dataSet) {
  @transient lazy val writer = FileLayerWriter(hadoopIngestPath)
  @transient lazy val reader = FileLayerReader(hadoopIngestPath)
  @transient lazy val attributeStore = FileAttributeStore(hadoopIngestPath)
}