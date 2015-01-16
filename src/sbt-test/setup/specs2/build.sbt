val specs2 = (project in file(".")).enablePlugins(PhantomJs)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "2.4.15" % "test",
  "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0"
)

javaOptions in Test ++= PhantomJs.setup(baseDirectory.value)
