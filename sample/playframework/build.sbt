libraryDependencies += "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0" % "test"

lazy val sample = Project(
  "sample", file(".")
).enablePlugins(PlayScala, PhantomJs).settings(
  scalaVersion := "2.11.4"
)

javaOptions in Test ++= PhantomJs.setup(baseDirectory.value)
