package test

import org.specs2.mutable._

import play.api._
import play.api.test._
import play.api.test.Helpers._

import com.novus.salat.util._
import models._
import com.mongodb.casbah.Imports._

class ModelSpec extends Specification with Logging {
  sequential

  def mongoTestDatabase() = Map("mongo.dbname" -> "muse_db_test")

  object FakeApp extends FakeApplication(additionalConfiguration = mongoTestDatabase())

  step {
    running(FakeApp) {
      val stake = Stakeholder("SH1", "user", "general user", "User")
      val proj = Project(name = "test1",
                         description = "test project",
                         stakeholders = stake :: Nil
                       )
      Project.remove(MongoDBObject.empty)
      Project.collection.drop()
      Project.insert(proj)
      val req = Requirement(
        refId           =  "REQ1",
        version         =  1,
        title           =  "First  requirement",
        classification  =  new     Classification(packageName  =  "demo"),
        description     =  "Some   description",
        parentId        =  Some(proj.id)
      )
      Project.addRequirement(req)
    }
  }

  running(FakeApp) {
    val Some(project) = Project.findByName("test1")

    "Project" should {

      "be retrieved by name" in {
        project must beAnInstanceOf[Project]
        project.description must endWith("project")
      }

      "have exactly one requirement" in {
        {
          val head = SalatDAOUtils.exactlyOne(project.requirements)
          head must be(project.requirements.head)
          head.title must beEqualTo("First requirement")
        } must not( throwA[Throwable] )
      }

      "have one stakeholder" in {
        {
          val head = SalatDAOUtils.exactlyOne(project.stakeholders)
          head must be(project.stakeholders.head)
          head.refId must beEqualTo("SH1")
        } must not( throwA[Throwable] )
      }

      "not have duplicates with the same name" in {
        {
          val proj = Project(name = "test1", description = "test project")
          Project.insert(proj)
        } must throwA[MongoException]

        Project.find(MongoDBObject("name" -> "test1")).count must_== 1
      }
    }
  }

  running(FakeApp) {
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
          Project.addRequirement(req)
        } must not (throwA[MongoException])

      }
    }
  }
}
