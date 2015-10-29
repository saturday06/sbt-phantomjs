val scalatest = (project in file(".")).enablePlugins(PhantomJs)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.codeborne" % "phantomjsdriver" % "1.2.1"
)

javaOptions in Test ++= PhantomJs.setup(baseDirectory.value)
