package controllers

import play.core.Router.JavascriptReverseRoute

object jsRoutes {
    
  /*
   * using reflection build up an Array of JS reverse routes
   * by invoking js reverse router for each controller action method. 
   */ 
	private lazy val routeCache: Map[String,Array[JavascriptReverseRoute]] = {
		val routeFiles = Map(
			"routes-all.js"	-> ( // filename to write to
				Array(
					classOf[foo.routes.javascript],
					classOf[bar.routes.javascript]
				)
			)
		)
		routeFiles map{ case(filename,routes) =>
		  (filename, 
		  routes.map{ clazz=>
				clazz.getFields.map(_.get(null)).flatMap { c=>
					c.getClass.getDeclaredMethods.map(
						_.invoke(c).asInstanceOf[JavascriptReverseRoute]
					)
				}
			}.flatten.filterNot(_.name.contains("Assets")) )
	  }
	}
	
  /*
	 * writes js route file(s) to specified UI path
   */
	def routes2File(uiPath: String) = {
	  routeCache.map{ case(file,routes)=>
	    print2File( new java.io.File(uiPath + file) ) {p=> 
	      p.println(
					routesHeader + routesBody(routes) + routesFooter
			  )
			}
	  }
	}
	
	/*
	 * generates js routes source code
	 *
	 * need to workaround issue where sub project
   * javascript url prefix is always set to root "/" when called from this, 
   * the parent project. 
   * 
   * solution is to parse conf/routes file and replace
   * default javascript url:"/" entry with actual sub project route prefix 
	*/
	private def routesBody(routes: Array[JavascriptReverseRoute]): String = {
	  val urlRoot = "url:\"/\"" // js prefix to replace 
		routes.map{route=>
    	val pkgParts = route.name.replace("controllers.","").split("\\.")
	    val (subproject,pkg) = 
	    	(pkgParts(0), pkgParts.mkString(".")) //;println(subproject+": "+pkg)
	    	
  		val urlReplace = "url:\"" +
  			(mainConf2Tuple.filter(_._2 == subproject) match {
  		  	case List((uri,_)) if uri != "/" => uri
  		  	case _ => ""
  		  }) + "/\"" // add root or trailing slash
  		//println(urlReplace)

			// generate route's js method source with some cleanup
			s"""
			|_nS('${pkg}');
			|_root.${pkg} =
			|${route.f.
			  replaceAll("}\\)\n", "}\\);\n").
			  replaceAll("}\n", "};\n").
			  replaceAll(urlRoot, urlReplace)
			}
			|""".stripMargin
    }.mkString
	} 
	
	private val routesHeader = 
"""
(function(_root){
var _nS = jRouter.path2Method
var _qS = jRouter.qString
var _s 	= jRouter.secure
var _wA = jRouter.route  
"""
	
	private val routesFooter = 
"""
})(jRouter.root);
"""

	// parses conf/routes file to List((uri, package name)) of sub project routes 
	private lazy val mainConf2Tuple: List[Tuple2[String,String]] = {
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
  
	private def print2File(f: java.io.File)(op: java.io.PrintWriter => Unit) {
		val p = new java.io.PrintWriter(f)
		try { op(p) } finally { p.close() }
	}
	
}

