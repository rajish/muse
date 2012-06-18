import sbt._
import Keys._
import PlayProject._
import sbtscalaxb.Plugin._
import ScalaxbKeys._

import org.ensime.sbt.Plugin.Settings.ensimeConfig
import org.ensime.sbt.util.SExp._

object ApplicationBuild extends Build {

    val appName         = "muse"
    val appVersion      = "1.0-SNAPSHOT"

    val novusRels = "repo.novus rels" at "http://repo.novus.com/releases/"

    val appDependencies = Seq(
      "se.radley" %% "play-plugins-salat" % "1.0.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA,
                           settings = Defaults.defaultSettings ++ buildInfoSettings ++ scalaxbSettings
                         ).settings(
      lessEntryPoints <<= baseDirectory(_ / "app" / "assets" / "css" ** "style.less"),
      // scalaxb settings
      sourceGenerators in Compile <+= buildInfo,
      buildInfoKeys := Seq[Scoped](name, version, scalaVersion, sbtVersion),
      buildInfoPackage := "hello",
      packageName in scalaxb in Compile := "models",
      protocolPackageName in scalaxb in Compile := Some("zpProtocol"),
      sourceGenerators in Compile <+= scalaxb in Compile,
      // salat settings
      routesImport += "se.radley.plugin.salat.Binders._",
      templatesImport += "org.bson.types.ObjectId",
      // ensime settings
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
}
