package test

import org.specs2.mutable._

import play.api._
import play.api.test._
import play.api.test.Helpers._

import com.mongodb.casbah.Imports._


class ModelSpec extends Specification {
  import models._
  import com.mongodb.casbah.Imports._

  def mongoTestDatabase() = Map("mongo.url" -> "muse-database-test")

  object FakeApp extends FakeApplication(additionalConfiguration = mongoTestDatabase())

  step {
    running(FakeApp) {
      val stake = Stakeholder("SH1", "user", "general user", "User")
      val req = Requirement("REQ1", 1, "First requirement", Strength.Shall, new Classification(packageName = "demo"), "Some description", Nil, new ObjectId, Nil, Nil)
      val reqs = MongoDBList.newBuilder
      reqs += req
      val proj = Project("test1",
                         "test project",
                         reqs.result,   // requirements
                         stake :: Nil,  // stakeholders
                         Nil,           // usecases
                         Nil            // glossary
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
        project.description must beEqualTo("test project")
      }

      "have one requirement" in {
        project.requirements must not be empty
        project.requirements.size  must_== 1
        project.requirements.getAs[Requirement](0).title must beEqualTo("First requirement")
      }

      "have one stakeholder" in {
        project.stakeholders must not be empty
        project.stakeholders.size must_== 1
        project.stakeholders.head.title must beEqualTo("user")
      }

      "not have duplicates with the same name" in {
        val proj = Project("test1", "test project", Nil, Nil, Nil, Nil)
        Project.insert(proj) must throwAn[Error]
        Project.find(MongoDBObject("name" -> "test1")).count must_== 1
      }
    }
  }

  "Requirement" should {
    "be retrieved by refId" in {
      running(FakeApp) {
        val Some(requirement) = Requirement.findByRef("REQ1")
        requirement must beAnInstanceOf[Requirement]
        requirement.refId must beEqualTo("REQ1")
        requirement.title must startWith("First")
      }
    }

    "not have duplicates with the same refId" in {
      running(FakeApp) {
        val req = Requirement("REQ1", 1, "First requirement", Strength.Shall, new Classification(packageName = "demo"), "Some description", Nil, new ObjectId, Nil, Nil)
        Requirement.insert(req)  must throwAn[Error]
        Requirement.find(MongoDBObject("refId" -> "REQ1")).count must_== 1
      }
    }
  }
}
