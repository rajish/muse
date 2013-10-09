import org.ensime.sbt.Plugin.Settings.ensimeConfig

import org.ensime.sbt.util.SExp._

import Dependencies._

name := "muse2"

libraryDependencies ++= Seq(cache) ++ museDependencies

play.Project.playScalaSettings

// lessEntryPoints <<= baseDirectory(_ / "app" / "assets" / "css" ** "style.less")

coffeescriptOptions := Seq("bare", "/usr/local/bin/coffee -p")

ensimeConfig in ThisBuild := sexp(
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
