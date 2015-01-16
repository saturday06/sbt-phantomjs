val specs2 = (project in file(".")).enablePlugins(PhantomJs)

(test in Test) <<= (test in Test) dependsOn (installPhantomJs)

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "2.4.15" % "test",
  "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0"
)
