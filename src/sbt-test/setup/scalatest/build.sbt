val scalatest = (project in file(".")).enablePlugins(PhantomJs)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0"
)

javaOptions in Test ++= PhantomJs.setup(baseDirectory.value)
