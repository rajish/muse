package object models {
  import reactivemongo.api._
  import play.modules.reactivemongo._
  import reactivemongo.bson.Macros

  implicit val projectHandler = Macros.handler[Project]

}
