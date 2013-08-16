package models

import play.api.Play.current
import org.joda.time._, format._

import reactivemongo.bson._
import play.modules.reactivemongo._, ReactiveMongoPlugin._
import scala.concurrent.ExecutionContext.Implicits.global
import reactivemongo.api.indexes._
/**
 * Helper for pagination.
 */
case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

trait TimeStamps {
}

// ------------------------------ Project ------------------------------
case class Project(
  id: Option[BSONObjectID],
  name: String,
  description: String,
  created: Option[BSONDateTime],
  modified: Option[BSONDateTime]
)

object Project  {
  implicit val projectHandler = Macros.handler[Project]

  // implicit object ProjectReader extends BSONDocumentReader[Project] {
  //   def read(doc: BSONDocument): Project =
  //     Project(
  //       doc.getAs[BSONObjectID] ("_id"         ),
  //       doc.getAs[String]   ("name"        ).get,
  //       doc.getAs[String]   ("description" ).get,
  //       doc.getAs[BSONDateTime] ("created"     ).map  (dt => new DateTime (dt.value ) ),
  //       doc.getAs[BSONDateTime] ("modified"    ).map (dt => new DateTime (dt.value  ) )
  //     )
  // }

  // implicit object ProjectWriter extends BSONDocumentWriter[Project] {
  //   def write(proj: Project): BSONDocument =
  //     BSONDocument(
  //       "_id"         -> proj.id.getOrElse(BSONObjectID.generate),
  //       "name"        -> proj.name,
  //       "description" -> proj.description,
  //       "created"     -> proj.created.map(d => BSONDateTime(d.getMillis)),
  //       "modified"    -> proj.modified.map(d => BSONDateTime(d.getMillis))
  //     )
  // }
}
