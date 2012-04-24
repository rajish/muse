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
  val req = Requirement("REQ1", 1, "First requirement", Strength.Shall, new Classification(packageName = "demo"), "Some description", Nil, new ObjectId, Nil, Nil)
  val proj = Project("test1", "test project", req :: Nil, Nil, Nil, Nil)

  step {
    running(FakeApp) {
      Project.remove(MongoDBObject.empty)
      Project.insert(proj)
    }
  }

  "Project" should {
    "be retrieved by name" in {
      running(FakeApp) {
        val Some(project) = Project.findOne(MongoDBObject("name" -> "test1"))
        project.description must endWith("project")
      }
    }
    "have one requirement" in {
      running(FakeApp) {
        val Some(project) = Project.findOne(MongoDBObject("name" -> "test1"))
        project.requirements must not be empty
        project.requirements.size  must_== 1
        project.requirements must contain(req).only
      }
    }
  }
}
