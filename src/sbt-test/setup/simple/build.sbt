val simple = (project in file(".")).enablePlugins(PhantomJs)

libraryDependencies += "com.codeborne" % "phantomjsdriver" % "1.2.1"

javaOptions ++= PhantomJs.setup(baseDirectory.value)
