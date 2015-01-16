val simple = (project in file(".")).enablePlugins(PhantomJs)

libraryDependencies += ("com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0")

javaOptions += PhantomJs.setup(baseDirectory.value)
