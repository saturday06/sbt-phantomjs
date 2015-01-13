val rootProject = (project in file(".")).enablePlugins(jp.leafytree.sbt.SbtPhantomJs)

(run in Compile) <<= (run in Compile) dependsOn (installPhantomJs)
