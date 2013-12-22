import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
	import RoutesJS._

  val appName         = "reverse-router-js"
  val appVersion      = "1.1"

  lazy val foo = play.Project(
  	appName + "-foo", appVersion, path = file("module/foo"))

  lazy val bar = play.Project(
  	appName + "-bar", appVersion, path = file("module/bar"))

  lazy val aaMain  = play.Project(appName + "-main", appVersion).settings(
		routesJSTask
  ).
  dependsOn(foo,bar).aggregate(foo,bar)

}
