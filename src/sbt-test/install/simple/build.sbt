val simple = (project in file(".")).enablePlugins(PhantomJs)

(run in Compile) <<= (run in Compile) dependsOn (installPhantomJs)

libraryDependencies += ("com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0")
