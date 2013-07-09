package controllers.bar

import play.api._
import play.api.mvc._

import com.company.utils.router

object Bar extends Controller {
  
  def index = Action {
  	Ok("Bar Says Hi")
  }
  
  def qString(arg1: Option[Int], arg2: Option[String]) = Action {
  	val uri = router.foo.FooQString(arg1,arg2)
  	Ok("Bar doesn't depend on Foo but it can still link to it: "+uri)
  }

}
