import sbt._
import Keys._
import play.Project._
import sbtscalaxb.Plugin._
import ScalaxbKeys._

import org.ensime.sbt.Plugin.Settings.ensimeConfig
import org.ensime.sbt.util.SExp._

object ApplicationBuild extends Build {

  val appName         = "muse"
  val appVersion      = "1.0-SNAPSHOT"

  val novusRels = "repo.novus rels" at "http://repo.novus.com/releases/"
  val sonatypeSnapshots = "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  val appDependencies = Seq(
    "org.reactivemongo" %% "play2-reactivemongo" % "0.10-SNAPSHOT",
    "org.webjars" %% "webjars-play" % "(2.1.0-2,)",
    "org.webjars" % "angularjs" % "(1.1.5-1,)",
    "org.webjars" % "bootstrap" % "(2.3.2,)"
  )

  val main = play.Project(appName, appVersion, appDependencies,
    settings = Defaults.defaultSettings ++ scalaxbSettings
  ).settings(
    resolvers ++= Seq(sonatypeSnapshots),
    lessEntryPoints <<= baseDirectory(_ / "app" / "assets" / "css" ** "style.less"),
    coffeescriptOptions := Seq("bare", "/usr/local/bin/coffee -p"),
    ensimeConfig := sexp(
      key(":only-include-in-index"), sexp(
        "controllers\\..*",
        "models\\..*",
        "views\\..*",
        "play\\..*"
      ),
      key(":source-roots"), sexp(
        "/home/rajish/proj/muse/muse.play/app",
        "/home/rajish/proj/muse/muse.play/test",
        "/home/rajish/bin/play2/framework/src"
      )
    )
  )

  // val generate = TaskKey[Unit]("genxml", "Generates data models from XML.")
  // val generateTask = generate := {
  //   // scalaxb settings
  //   sourceGenerators in Compile <+= scalaxb in Compile,
  //   packageName in scalaxb in Compile := "models",
  //   protocolPackageName in scalaxb in Compile := Some("XuseProtocol"),
  // }
}
