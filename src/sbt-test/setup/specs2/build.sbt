val specs2 = (project in file(".")).enablePlugins(PhantomJs)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "2.4.15" % "test",
  "com.codeborne" % "phantomjsdriver" % "1.2.1"
)

javaOptions in Test ++= PhantomJs.setup(baseDirectory.value)
