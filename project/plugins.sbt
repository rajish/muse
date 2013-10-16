// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.0")

// The ENSIME plugin
addSbtPlugin("org.ensime" % "ensime-sbt-cmd" % "0.1.2")

// Auto-refresh plugin - https://github.com/jamesward/play-auto-refresh
addSbtPlugin("com.jamesward" %% "play-auto-refresh" % "0.0.5")

// sbt-yeoman
addSbtPlugin("com.tuplejump" % "sbt-yeoman" % "0.6.2")
