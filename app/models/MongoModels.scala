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

// ------------------------------ Common ------------------------------
case class Comment(
  description: String,
  author: ObjectId,
  date: DateTime = DateTime.now
) extends TimeStamps

case class Annotation(
  description: String,
  author: ObjectId,
  intendedAudience: List[ObjectId],
  comments: List[Comment]
) extends TimeStamps

// ------------------------------ Stakeholders ------------------------------
case class Stakeholder(
  refId: String,
  title: String,
  description: String,
  role: String // RoleEnum ??
)

// ------------------------------ Requirements ------------------------------
object Strength extends Enumeration {
  val May = Value("may")
  val Should = Value("should")
  val Shall = Value("shall")
}

object ReqType extends Enumeration {
  val Functional = Value("functional")
  val NonFunctional = Value("non-functional")
  val Constraint = Value("constraint")
  val Architectural = Value("architectural")
  val Technical = Value("technical")
  val Inverse = Value("inverse")
}

object ReqStatus extends Enumeration {
  val Proposed  = Value("proposed")
  val Agreed = Value("agreed")
  val OnHold = Value("on-hold")
  val Rejected = Value("rejected")
  val PartiallyImplemented = Value("partially-implemented")
  val Implemented = Value("implemented")
}

case class Classification(
  reqType: ReqType.Value = ReqType.Functional,
  status: ReqStatus.Value = ReqStatus.Proposed,
  packageName: String
)

case class Requirement(
  @Key("_id") id: ObjectId = new ObjectId,
  refId: String,
  version: BigDecimal,
  title: String,
  classification: Classification,
  description: String,
  projectId: Option[ObjectId] = None,
  strength: Strength.Value = Strength.Shall,
  parentId: Option[ObjectId] =  None
  // stakeholders: List[ObjectId] = Nil,
  // related: List[ObjectId] = Nil,
  // annotations: List[Annotation] = Nil
) extends TimeStamps {

  def addChild(r: Requirement): Unit =  {
    val req = r.copy(parentId = Option(id))
    Requirement.insert(req)
  }

}

object Requirement extends ModelCompanion[Requirement, ObjectId] {
  val collection = getCollection("requirements")

  val dao = new SalatDAO[Requirement, ObjectId](collection = collection) {
    collection.ensureIndex(MongoDBObject("refId" -> 1, "version" -> 1), "refId_version", unique = true)
    val children = new ChildCollection[Requirement, ObjectId](collection = getCollection("requirements"), parentIdField = "parentId"){}
  }

  def findByRef(ref: String) = findOne(MongoDBObject( "refId" -> ref))
}

// ------------------------------ Actors ------------------------------
case class Actor(
  stereotype: String,
  description: String,
  title: String
)

// ------------------------------ Use Case ------------------------------

case class Step(
  refId: String,
  description: String,
  alternateFlowId: ObjectId,
  condition: String,
  annotations: List[Annotation]
)

case class AlternateFlow(
  refId: String,
  title: String,
  steps: List[Step],
  rejoinStepId: ObjectId,
  isEnd: Boolean,
  annotations: List[Annotation]
)

case class Version(
  revision: BigDecimal,
  author: ObjectId,
  date: DateTime,
  description: String
)

case class DocumentHistory(
  currentVersion: BigDecimal,
  versions: List[Version]
)

case class UCProperties(
  trigger: String,
  goal: String,
  primaryActorId: ObjectId,
  secondaryActorId: List[ObjectId],
  prerequisistes: String,
  successOutcome: String,
  failureOutcome: String,
  priority: Int,
  complexity: Int
)

case class UseCase(
  refId: String,
  name: String,
  description: String,
  documentHistory: DocumentHistory,
  properties: List[Option[String]],
  mainFlow: List[Step],
  alternateFlows: List[AlternateFlow],
  excetpionFlows: List[AlternateFlow] // rejoinStepId always None
)


// ------------------------------ Glossary ------------------------------
case class GlossaryEntry(
  title: String,
  abbreviation: String,
  description: String
)

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

  }
  def findByName( name: String) =  findOne(MongoDBObject("name" -> name))

}
