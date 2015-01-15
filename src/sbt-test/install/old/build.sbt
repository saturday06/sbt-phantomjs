val rootProject = (project in file(".")).enablePlugins(SbtPhantomJs)

(run in Compile) <<= (run in Compile) dependsOn (installPhantomJs)
