val simple = (project in file(".")).enablePlugins(PhantomJs)

(run in Compile) <<= (run in Compile) dependsOn (installPhantomJs)
