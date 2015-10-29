val old = (project in file(".")).enablePlugins(SbtPhantomJs)

(run in Compile) <<= (run in Compile) dependsOn (installPhantomJs)

libraryDependencies += "com.codeborne" % "phantomjsdriver" % "1.2.1"
