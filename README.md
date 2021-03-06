play-reverse-router-js
======================

Generates global javascript reverse router as static file(s) at compile time for sub project enabled builds

Motivation
----------

As of Play 2.1 javascript reverse routes are generated at runtime and are limited to the sub project in which they defined. This SBT Task generates static js route file(s) at compile time without being limited to any particular sub project (i.e. you can mix & match sub projects as desired, creating, for example, public and private js route files, or a global all-in-one file).

How to Use
----------

From main project/ directory copy RoutesJS.scala and TaskUtils.scala to your main project/ and then add to your Build.scala

    object ApplicationBuild extends Build {
      import RoutesJS._
      ....
      lazy val aaMain  = play.Project(appName + "-main", appVersion).settings(
        routesJSTask
      )
      
Also copy main project's app/controllers/jsRoutes.scala into the same location in your project.

You will need to specify relative path to Play framework install by editing the "playPath" val in RoutesJS.scala. In app/controllers/jsRoutes.scala edit the "routeFiles" Map under "routeCache" lazy val according to your setup.

By default js routes are written to public/javascripts, which contains 2 files: jRouter.js (a cleaned up version of Play's obfuscated js router) and routes-all.js, the generated sample routes. Update "absoluteUrl" and "websocketUrl" at bottom of jRouter.js accordingly. I use Grunt JS to combine/minify assets, not sure what Play offers on this front. Either way you'll need to include both files to generate reverse routes.

Usage:

    router.module.Controller.action(args)
    e.g. router.account.Account.edit(id)

(re)generate routes by running "routesJS" via play> prompt


