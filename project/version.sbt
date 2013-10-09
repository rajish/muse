val gitHeadCommitSha = settingKey[String]("current git commit SHA")

gitHeadCommitSha in ThisBuild := Process("git rev-parse HEAD").lines.head

val release = settingKey[Boolean]("")

release := sys.props("release") == "true"

version in ThisBuild := {
  val v = "1.0"
  if (release.value) {
    v
  } else {
    s"$v-${gitHeadCommitSha.value}"
  }
}
