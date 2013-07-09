package com.company.utils

object router {
  def makeQString(args: List[(String, Option[_])]): String = {
    val maybeArgs = args.flatMap{case(a,Some(b))=> Some(a->b.toString); case _ => None}
    if(!maybeArgs.isEmpty) {
      "?"+maybeArgs.map{case(k,v)=> k+"="+v}.mkString("&")
    } else ""
  }
  object foo {
	val prefix = "/foo"

	def FooIndex() = 
		s"${prefix}/index"

	def FooQString(arg1: Option[Int], arg2: Option[String]) = 
		s"${prefix}/qstring${makeQString(List("arg1", "arg2").zip(List(arg1, arg2)))}"
}

object bar {
	val prefix = "/bar"

	def BarIndex() = 
		s"${prefix}/index"

	def BarQString(arg1: Option[Int], arg2: Option[String]) = 
		s"${prefix}/qstring${makeQString(List("arg1", "arg2").zip(List(arg1, arg2)))}"
}

}

