package models

import play.api.Play.current
import org.joda.time._, format._

import reactivemongo.bson._
import play.modules.reactivemongo._, ReactiveMongoPlugin._
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.api.indexes._

case class Project(
  id: Option[BSONObjectID],
  name: String,
  description: String,
  created: Option[BSONDateTime],
  modified: Option[BSONDateTime]
)

object Project  {
  implicit val projectHandler = Macros.handler[Project]

}
