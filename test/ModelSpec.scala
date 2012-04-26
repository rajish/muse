package test

import org.specs2.mutable._

import play.api._
import play.api.test._
import play.api.test.Helpers._

import com.novus.salat.util._

class ModelSpec extends Specification {
  import models._
  import com.mongodb.casbah.Imports._

  def mongoTestDatabase() = Map("mongo.url" -> "muse-database-test")

  object FakeApp extends FakeApplication(additionalConfiguration = mongoTestDatabase())

  step {
    running(FakeApp) {
      val stake = Stakeholder("SH1", "user", "general user", "User")
      val req = Requirement("REQ1", 1, "First requirement", Strength.Shall, new Classification(packageName = "demo"), "Some description", Nil, new ObjectId, Nil, Nil)
      val proj = Project("test1",
                         "test project",
                         req :: Nil,    // requirements
                         stake :: Nil, // stakeholders
                         Nil,          // usecases
                         Nil           // glossary
                       )
      Project.remove(MongoDBObject.empty)
      Project.insert(proj)
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
        SalatDAOUtils.exactlyOne(project.requirements) must be(project.requirements.head)
        project.requirements.size  must_== 1
        project.requirements.head.title must beEqualTo("First requirement")
      }

      "have one stakeholder" in {
        project.stakeholders must not be empty
      }

      "not have duplicates with the same name" in {
        val proj = Project("test1", "test project", Nil, Nil, Nil, Nil)
        Project.insert(proj) must throwAn[Error]
        Project.find(MongoDBObject("name" -> "test1")).count must_== 1
      }
    }
  }

  running(FakeApp) {
    val Some(requirement) = Requirement.findByRef("REQ1")
    "Requirement" should {
      "be retrieved by refId" in {
        requirement must beAnInstanceOf[Requirement]
        requirement.refId must beEqualTo("REQ1")
        requirement.title must startWith("First")
      }

      "not have duplicates with the same refId" in {
        val req = Requirement("REQ1", 1, "First requirement", Strength.Shall, new Classification(packageName = "demo"), "Some description", Nil, new ObjectId, Nil, Nil)
        Requirement.insert(req)  must throwAn[Error]
        Requirement.find(MongoDBObject("refId" -> "REQ1")).count must_== 1
      }
    }
  }
}
