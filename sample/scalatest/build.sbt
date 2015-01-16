val scalatest = (project in file(".")).enablePlugins(PhantomJs)

(test in Test) <<= (test in Test) dependsOn (installPhantomJs)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.github.detro.ghostdriver" % "phantomjsdriver" % "1.1.0"
)
