import sbt._
import Keys._
import PlayProject._

import org.ensime.sbt.Plugin.Settings.ensimeConfig
import org.ensime.sbt.util.SExp._

object ApplicationBuild extends Build {

    val appName         = "muse"
    val appVersion      = "1.0-SNAPSHOT"

    val novusRels = "repo.novus rels" at "http://repo.novus.com/releases/"

    val appDependencies = Seq(
      "se.radley" %% "play-plugins-salat" % "1.0.1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      routesImport += "se.radley.plugin.salat.Binders._",
      templatesImport += "org.bson.types.ObjectId",
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
