import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
	import RoutesJS._

  val appName         = "reverse-router-js"
  val appVersion      = "1.1"

  lazy val common = play.Project(
  	appName + "-common", appVersion, path = file("module/common"))
 		
  lazy val foo = play.Project(
  	appName + "-foo", appVersion, path = file("module/foo")).
  	dependsOn(common)

  lazy val bar = play.Project(
  	appName + "-bar", appVersion, path = file("module/bar")).
  	dependsOn(common)

  lazy val aaMain  = play.Project(appName + "-main", appVersion).settings(
		routesJSTask
  ).
  dependsOn(common,foo,bar).aggregate(common,foo,bar)

}
