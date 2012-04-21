package models

import play.api._

import java.util.{Date}

import play.api.Play.current


import com.novus.salat._
import com.novus.salat.global._
import com.novus.salat.dao._
import com.novus.salat.annotations._
import com.mongodb.casbah.Imports._
import models.SalatImports._

import org.scala_tools.time.Imports._

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

// ------------------------------ Stakeholders ------------------------------
case class Stakeholder(@Key("_id") id: ObjectId = new ObjectId,
                       refId: String,
                       title: String,
                       description: String,
                       role: String // RoleEnum
                     )

// ------------------------------ Requirements ------------------------------
object Strength extends Enumeration {
  val May = Value("may")
  val Should = Value("should")
  val Shall = Value("shall")
}

case class Classification(reqType: String, //ReqType,
                          status: String, //ReqStatus,
                          packageName: String
                         )

case class Requirement(@Key("_id") id: ObjectId = new ObjectId,
                       refId: String,
                       version: BigDecimal,
                       title: String,
                       strength: Strength.Value = Strength.Shall,
                       classification: Classification,
                       description: String,
                       stakeholders: List[ObjectId],
                       parentId: ObjectId
                     )

object RequirementDAO extends SalatDAO[Requirement, ObjectId](collection = mongoCollection("requirements")) {
  val children = new ChildCollection[Requirement, ObjectId](collection = mongoCollection("requirements"), parentIdField = "parentId")
}

// ------------------------------ Actors ------------------------------
case class Actor(@Key("_id") id: ObjectId = new ObjectId,
                 stereotype: String,
                 description: String,
                 title: String
               )

case class UseCase(@Key("_id") id: ObjectId = new ObjectId,
                   refId: String,
                   name: String
                 )

case class GlossaryEntry(@Key("_id") id: ObjectId = new ObjectId,
                         title: String,
                         abbreviation: String,
                         description: String
                       )

case class Project(@Key("_id") id: ObjectId = new ObjectId,
                   name: String,
                   created: Option[Date],
                   modified: Option[Date],
                   description: Option[String],
                   requirements: List[Requirement],
                   stakeholders: List[Stakeholder],
                   usecases: List[UseCase],
                   glossary: List[GlossaryEntry]
                 )
