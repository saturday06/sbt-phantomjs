val old = (project in file(".")).enablePlugins(SbtPhantomJs)

(run in Compile) <<= (run in Compile) dependsOn (installPhantomJs)

libraryDependencies += ("com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0")
