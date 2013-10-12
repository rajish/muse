package controllers

import play.api._
import play.api.mvc._
import play.templates._
import play.api.templates._
import scala.reflect.runtime.{universe => ru}

object Application extends Controller {

  val runtimeMirror = ru.runtimeMirror(getClass.getClassLoader)

  def index = Action {
    Ok(views.html.index("muse"))
  }

  def partial(file: String) = Action {
    try {
      val module = runtimeMirror.staticModule("views.html.partials." + file)
      val instance = runtimeMirror.reflectModule(module).instance
      val im = runtimeMirror.reflect(instance)
      val apply = im.symbol.typeSignature.member(ru.newTermName("apply")).asMethod
      Ok(im.reflectMethod(apply)().asInstanceOf[Html])
    } catch {
      case e: Exception =>
        Logger.error(s"ERROR serving partial '$file': $e" )
        Ok(views.html.partials.error(file))
    }
  }

}
