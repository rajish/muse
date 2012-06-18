package models

import com.mongodb.casbah.Imports._
import org.scala_tools.time.Imports._
import play.api.Play.current
import com.novus.salat._
import se.radley.plugin.salat._
import com.novus.salat.dao.{ SalatDAO, ModelCompanion }

/**
 * Helper for pagination.
 */
case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

@Salat
trait TimeStamps {
  val created: DateTime = DateTime.now
  var modified: DateTime = DateTime.now
}

// ------------------------------ Project ------------------------------
case class Project(
  @Key("_id") id: ObjectId = new ObjectId,
  name: String,
  description: String
) extends TimeStamps {

  def addRequirement(r: Requirement) {
    val req = r.copy(projectId = Option(id))
    Requirement.insert(r)
  }
}

object Project extends ModelCompanion[Project, ObjectId] {
  val collection = getCollection("projects")
  val dao = new SalatDAO[Project, ObjectId](collection = collection){

    collection.ensureIndex(MongoDBObject("name" -> 1), "name", unique = true)

    val requirements = new ChildCollection[Requirement, ObjectId](
      collection = getCollection("requirements"), parentIdField = "projectId"){}
    val useCases     = new ChildCollection[UseCase, ObjectId](
      collection = getCollection("use_cases"), parentIdField = "projectId"){}
    val glossary     = new ChildCollection[GlossaryEntry, ObjectId](
      collection = getCollection("glossary"), parentIdField = "projectId"){}
  }
  def findByName( name: String) =  findOne(MongoDBObject("name" -> name))

}
