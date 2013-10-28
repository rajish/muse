package controllers.api.v1

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.concurrent._
import play.api.libs.iteratee._
import play.api.Play.current
import play.api.libs.iteratee.{Enumerator, Iteratee}

import scala.concurrent.Future
import scala.concurrent.duration._

import reactivemongo.api._
import play.modules.reactivemongo._
import play.modules.reactivemongo.json.collection.JSONCollection

import models._

object Project extends Controller with MongoController {

  def collection: JSONCollection = db.collection[JSONCollection]("projects")

  def getAll = Action.async {
    val cursor: Cursor[JsObject] = collection.find(Json.obj()).cursor[JsObject]
    val futureProjectsList: Future[List[JsObject]] = cursor.collect[List](10)
    futureProjectsList map(x => Ok(Json.toJson(x)))
  }

  def get(id: String) = Action.async {
    Future(NotImplemented)
  }

  def create = Action.async {
    Future(NotImplemented)
  }

  def update = Action.async {
    Future(NotImplemented)
  }

  def delete = Action.async {
    Future(NotImplemented)
  }
}
