import org.ensime.sbt.Plugin.Settings.ensimeConfig

import org.ensime.sbt.util.SExp._

name := "muse2"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  cache,
    "org.reactivemongo" %% "play2-reactivemongo" % "(0.10-SNAPSHOT,)",
    "org.webjars" %% "webjars-play" % "(2.1.0-2,)",
    "org.webjars" % "angularjs" % "(1.1.5-1,)",
    "org.webjars" % "bootstrap" % "(2.3.2,)"
)

lessEntryPoints <<= baseDirectory(_ / "app" / "assets" / "css" ** "style.less")

coffeescriptOptions := Seq("bare", "/usr/local/bin/coffee -p")

ensimeConfig := sexp(
  key(":only-include-in-index"), sexp(
    "controllers\\..*",
    "models\\..*",
    "views\\..*",
    "play\\..*"
  ),
  key(":source-roots"), sexp(
    "/home/rajish/proj/muse2/app",
    "/home/rajish/proj/muse2/test",
    "/home/rajish/bin/play2/framework/src"
  )
)

play.Project.playScalaSettings
