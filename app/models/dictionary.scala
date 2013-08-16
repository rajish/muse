// Generated by <a href="http://scalaxb.org/">scalaxb</a>.
package models


case class Dictionary(entry: Seq[models.DictionaryEntryType] = Nil,
  any: Seq[scalaxb.DataRecord[Any]] = Nil,
  modelu45version: String,
  attributes: Map[String, scalaxb.DataRecord[Any]])


case class DictionaryEntryType(value: Seq[models.ValueType] = Nil,
  key: String,
  attributes: Map[String, scalaxb.DataRecord[Any]])


case class ValueType(value: String,
  xmllang: Option[String] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]])
