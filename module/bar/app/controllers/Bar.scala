package controllers.bar

import play.api._
import play.api.mvc._

object Bar extends Controller {
  
  def index = Action {
  	Ok("Bar Says Hi")
  }
  
  def qString(arg1: Option[Int], arg2: Option[String]) = Action {
  	Ok("Bar js route should support query string")
  }

}
