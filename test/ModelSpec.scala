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
        val head = SalatDAOUtils.exactlyOne(project.requirements) //must throwA[Throwable]
        head must be(project.requirements.head)
        head.title must beEqualTo("First requirement")
      }

      "have one stakeholder" in {
        val head = SalatDAOUtils.exactlyOne(project.stakeholders)
        head must be(project.stakeholders.head)
        head.refId must beEqualTo("SH1")
      }

      "not have duplicates with the same name" in {
        // try {
          val proj = Project("test1", "test project", Nil, Nil, Nil, Nil) must throwA[MongoException]
          // Project.insert(proj) must throwA[MongoException]
        // } catch {
        //   case me: MongoException => success
        //   case _ => failure
        // }
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

      "not have duplicates with the same refId" in {
        // try {
          val req = Requirement("REQ1", 1, "First requirement",
                                Strength.Shall,
                                new Classification(packageName = "demo"),
                                "Some description", Nil,
                                new ObjectId, Nil, Nil)
          Requirement.insert(req)  must throwAn[MongoException]
        // } catch {
        //   case me: MongoException => printf("Got MongoException"); success
        //   case _ => failure
        // }
        Requirement.find(MongoDBObject("refId" -> "REQ1")).count must_== 1
      }
    }
  }
}
