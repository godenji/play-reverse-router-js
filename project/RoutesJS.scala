import sbt._
import Keys._

/*
 * task to generate javascript reverse router as static file at compile time
 *
 * note: sbt 0.12 uses scala 2.9 and Play 2.1 uses scala 2.10, thus reflection required
 */
object RoutesJS extends TaskUtils {
	val routesJS = TaskKey[Unit]("routesJS", "Generates javascript routes as static file")
	lazy val routesJSTask = routesJS <<= scalaInstance map { si=>
		
		val projectPath 	= System.getProperty("user.dir") + "/"
		val uiPath 				= projectPath + "/public/javascripts/" // >> change accordingly
	
		val targetDir = // project compilation targets to add to classpath
			"target/scala-"+getBinaryVersion(si)+"/classes/"
		
		val playPath = List(new java.net.URL( 
			// >> specify relative path to Play framework install
			"file:" + getFrameworkPath("bin/Play22") + targetDir)
		)
		
		val appPaths = // i.e. parent project "/" plus "/modules/module-name"
			(List("/") ++ getModules.map(name=>"/module/"+name+"/")).map(x=>
				new java.net.URL("file:" + projectPath + x + targetDir)
			)
			
		val classPath = (appPaths ++ playPath).toArray
		//classprojectPath foreach println
		
		val appLoader: ClassLoader = new java.net.URLClassLoader(classPath, si.loader)
		val clazz = appLoader.loadClass("controllers.jsRoutes")
		val invoker = clazz.getMethod("routes2File", classOf[String])
		try { invoker.invoke(null, uiPath) } 
		catch { case e: Exception => println(e.getCause) }
		
		()
	} dependsOn(compile in Compile)
	
}
