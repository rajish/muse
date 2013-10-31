// Generated by <a href="http://scalaxb.org/">scalaxb</a>.
package models


case class IncludeType(mixed: Seq[scalaxb.DataRecord[Any]] = Nil,
  href: Option[java.net.URI] = None,
  parse: models.ParseType,
  xpointer: Option[String] = None,
  encoding: Option[String] = None,
  accept: Option[String] = None,
  acceptLanguage: Option[String] = None,
  attributes: Map[String, scalaxb.DataRecord[Any]]) extends FallbackTypeOption

trait IncludeTypeOption
trait ParseType

object ParseType {
  def fromString(value: String, scope: scala.xml.NamespaceBinding): ParseType = value match {
    case "xml" => Xml
    case "text" => Text

  }
}

case object Xml extends ParseType { override def toString = "xml" }
case object Text extends ParseType { override def toString = "text" }


case class FallbackType(mixed: Seq[scalaxb.DataRecord[Any]] = Nil,
  attributes: Map[String, scalaxb.DataRecord[Any]]) extends IncludeTypeOption

trait FallbackTypeOption
