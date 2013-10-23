import org.ensime.sbt.Plugin.Settings.ensimeConfig

import org.ensime.sbt.util.SExp._

import com.tuplejump.sbt.yeoman.Yeoman

import Dependencies._

name := "muse"

libraryDependencies ++= Seq(cache) ++ museDependencies

play.Project.playScalaSettings

Yeoman.yeomanSettings

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
