package test

import org.specs2.mutable._

import play.api._
import play.api.test._
import play.api.test.Helpers._

import com.novus.salat.util._
import models._
import com.mongodb.casbah.Imports._

class ModelSpec extends Specification with Logging {
  override def is = args(sequential = true) ^ super.is

  val db_name = "muse_db_test"

  def mongoTestDatabase() = Map("mongodb.default.db" -> db_name)

  object FakeApp extends FakeApplication(additionalConfiguration = mongoTestDatabase())

  running(FakeApp) {
    log.trace("Database setup.")
    MongoConnection().dropDatabase(db_name)
    val stake = Stakeholder("SH1", "user", "general user", "User")
    val proj = Project(name = "test1",
                       description = "test project"
                     )
    Project.insert(proj)
    val req = Requirement(
      refId           =  "REQ1",
      version         =  1,
      title           =  "First requirement",
      classification  =  new Classification(packageName  =  "demo"),
      description     =  "Some description"
    )
    proj.addRequirement(req)
  }

  running(FakeApp) {
    log.trace("Project tests.")
    val Some(project) = Project.findByName("test1")

    "Project" should {

      "be retrieved by name" in {
        project must beAnInstanceOf[Project]
        project.description must endWith("project")
      }

      "have exactly one requirement" in {
        val reqs = Project.dao.requirements.findByParentId(project.id).toList
        val head = SalatDAOUtils.exactlyOne(reqs)
        head must be(reqs.head)
        head.title must beEqualTo("First requirement")
        head.projectId must_== Option(project.id)
      }

      "have one stakeholder" in {
        SalatDAOUtils.exactlyOne(project.stakeholders) must not( throwA[Throwable] )
        val stakes = Project.dao.stakeholders.findByParentId(projec.id).toList
        val head = SalatDAOUtils.exactlyOne(stakes)
        head must be(stakes.head)
        head.refId must beEqualTo("SH1")
      }

      "not have duplicates with the same name" in {
        {
          val proj = Project(name = "test1", description = "duplicate test project")
          Project.insert(proj)
        } must throwA[MongoException]

        Project.find(MongoDBObject("name" -> "test1")).count must_== 1
      }
    }
  }

  running(FakeApp) {
    log.trace("Requirement tests.")
    val requirement = Requirement.findByRef("REQ1")
    "Requirement" should {
      "be retrieved by refId" in {
        requirement must beAnInstanceOf[Some[Requirement]]
        val Some(sreq) = requirement
        sreq.refId must beEqualTo("REQ1")
        sreq.title must startWith("First")
      }

      "allow duplicates with the same refId" in {
        {
          val Some(proj) = Project.findByName("test1")
          val req = Requirement(
            refId           =  "REQ1",
            version         =  1,
            title           =  "First  requirement",
            classification  =  new     Classification(packageName  =  "demo"),
            description     =  "Some   description",
            parentId        =  Some(proj.id)
          )
          proj.addRequirement(req)
        } must not (throwA[MongoException])

      }
    }
  }
}
