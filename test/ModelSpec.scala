package test

import org.specs2.mutable._

import play.api._
import play.api.test._
import play.api.test.Helpers._


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

      "have one requirement" in {
        project.requirements must not be empty
        project.requirements.size  must_== 1
        project.requirements.head.title must beEqualTo("First requirement")
      }

      "have one stakeholder" in {
        project.stakeholders must not be empty
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
    }
  }
}
