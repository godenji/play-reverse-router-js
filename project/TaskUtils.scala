
trait TaskUtils {
	
	def routesFile(dir: String, module: String) = 
		dir+module+"/conf/"+module+".routes"
	
	// extract sub project package names from main conf/routes
	def getModules = mainConf2Tuple.map(_._2)
	
	// parses conf/routes file to List((uri, package name)) of sub project routes 
	lazy val mainConf2Tuple: List[Tuple2[String,String]] = {
		scala.io.Source.fromFile("conf/routes").
	  getLines.filter(_.startsWith("->")).
	  map( 
 	  	_.replaceAll("(->|.Routes)", "").
  	 	split("\b").map(_.replaceAll("\\s+", " "))
 		).
  	toList.flatten.
    map(_.trim.split(" ").toList).
 		map{
  		case(List(a,b))=> Map(a->b)
  	}.flatten
  }
	
	def getBinaryVersion(si: sbt.ScalaInstance) = {
		si.actualVersion.split("\\.").dropRight(1).mkString(".")
	}
 
	def print2File(f: java.io.File)(op: java.io.PrintWriter => Unit) {
		val p = new java.io.PrintWriter(f)
		try { op(p) } finally { p.close() }
	}
  
}
