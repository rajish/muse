import sbt._

object Dependencies {
  // Versions
  val webjarsPlayVersion        = "(2.1.0-2,)"
  val angularjsVersion          = "(1.1.5-1,)"
  val bootstrapVersion          = "(2.3.2,)"
  val reactiveMongoVersion      = "(0.10-SNAPSHOT,)"
  val angularUiBootstrapVersion = "(0.6.0,)"
  val jqueryVersion             = "(2.0.3,)"

  // Libraries
  val webjarsPlay        = "org.webjars"       %% "webjars-play"         % webjarsPlayVersion
  val angularjs          = "org.webjars"       %  "angularjs"            % angularjsVersion
  val bootstrap          = "org.webjars"       %  "bootstrap"            % bootstrapVersion
  val angularUiBootstrap = "org.webjars"       %  "angular-ui-bootstrap" % angularUiBootstrapVersion
  val jquery             = "org.webjars"       %  "jquery"               % jqueryVersion
  val reactiveMongo      = "org.reactivemongo" %% "play2-reactivemongo"  % reactiveMongoVersion

  // Projects
  val museDependencies = Seq(reactiveMongo)
}
