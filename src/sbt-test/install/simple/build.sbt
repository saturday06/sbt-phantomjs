val simple = (project in file(".")).enablePlugins(PhantomJs)

(run in Compile) <<= (run in Compile) dependsOn (installPhantomJs)

libraryDependencies += "com.codeborne" % "phantomjsdriver" % "1.2.1"
