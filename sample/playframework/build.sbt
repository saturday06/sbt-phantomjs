libraryDependencies += "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0" % "test"

lazy val sample = Project(
  "sample", file(".")
).enablePlugins(PlayScala, SbtPhantomJs).settings(
  scalaVersion := "2.11.4"
)

(test in Test) <<= (test in Test) dependsOn(installPhantomJs)
