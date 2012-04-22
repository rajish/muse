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
      Project.remove(MongoDBObject.empty)
      val proj = Project("test1", "test project", Nil, Nil, Nil, Nil)
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
  }
}
