package controllers.foo

import play.api._
import play.api.mvc._

object Foo extends Controller {

	def index = Action {
  	Ok("Foo Says Hi")
  }

  def qString(arg1: Option[Int], arg2: Option[String]) = Action {
		Ok("Foo js route should support query string")
  }

}
