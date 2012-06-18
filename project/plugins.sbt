// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "sonatype-public" at "https://oss.sonatype.org/content/groups/public",
  "repo.codahale.com" at "http://repo.codahale.com",
  Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns))

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0.1")

addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.1.1")

addSbtPlugin("org.scalaxb" % "sbt-scalaxb" % "0.7.1")

// The ENSIME plugin
addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "0.0.10")
