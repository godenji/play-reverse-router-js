package controllers.foo

import play.api._
import play.api.mvc._

import com.company.utils.router

object Foo extends Controller {

	def index = Action {
  	Ok("Foo Says Hi")
  }

  def qString(arg1: Option[Int], arg2: Option[String]) = Action {
  	val uri = router.bar.BarQString(arg1,arg2)
  	Ok("Foo doesn't depend on Bar but it can still link to it: "+uri)
  }

}
