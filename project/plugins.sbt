logLevel := Level.Warn

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "0.2.1")

//addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

libraryDependencies += "org.scala-sbt" % "scripted-plugin" % sbtVersion.value
