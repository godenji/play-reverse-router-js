import sbt._
import Keys._

/*
 * task to generate javascript reverse router as static file at compile time
 */
object RoutesJS extends TaskUtils {
	val routesJS = TaskKey[Unit]("routesJS", "Generates javascript routes as static file")
	lazy val routesJSTask = routesJS <<= scalaInstance map { si=>
	
		val projectName		= "reverse-router-js"	
		val projectPath 	= System.getProperty("user.dir") + "/"
		val uiPath 				= projectPath + "/public/javascripts/" // change accordingly

  	val sbtDir = "/tmp/sbt" // sbt compile target
		val targetDir = // project compilation targets to add to classpath
			"scala-"+getBinaryVersion(si)+"/classes/"
		
	  // assumes your play installation lives in ~/bin/playframework
		val playPath = List(new java.net.URL( 
			"file:"+System.getProperty("user.home")+
			"/bin/playframework/framework/src/play/"+ targetDir
		))
		
		val appPaths = // i.e. parent project "/" plus "/modules/module-name"
			(List(projectName) ++ getModules.map(name=>"/module/"+name+"/")).map(x=>
				new java.net.URL(
					"file:" + List(sbtDir, module, targetDir).mkString("/")
				)
			)
			
		val classPath = (appPaths ++ playPath).toArray	
		val appLoader: ClassLoader = new java.net.URLClassLoader(classPath, si.loader)
		val clazz = appLoader.loadClass("controllers.jsRoutes")
		val invoker = clazz.getMethod("routes2File", classOf[String])
		try { invoker.invoke(null, uiPath) } 
		catch { case e: Exception => println(e.getCause) }
		
		()
	} dependsOn(compile in Compile)
	
}
