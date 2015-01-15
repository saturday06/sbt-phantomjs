import SonatypeKeys._

sbtPlugin := true

name := "sbt-phantomjs"

organization := "jp.leafytree.sbt"

profileName := "jp.leafytree"

version := "0.1.4-SNAPSHOT"

sonatypeSettings

ScriptedPlugin.scriptedSettings

scriptedLaunchOpts := { scriptedLaunchOpts.value ++ Seq("-Dplugin.version=" + version.value) }

libraryDependencies ++= Seq(
  "org.apache.maven.shared" % "maven-invoker" % "2.1.1"
)

pomExtra := {
  <url>https://github.com/saturday06/sbt-phantomjs</url>
  <licenses>
    <license>
      <name>The Apache Software License, Version 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    </license>
  </licenses>
  <scm>
    <url>https://github.com/saturday06/sbt-phantomjs.git</url>
    <connection>scm:git:https://github.com/saturday06/sbt-phantomjs.git</connection>
    <developerConnection>scm:git:git@github.com:saturday06/sbt-phantomjs.git</developerConnection>
  </scm>
  <developers>
    <developer>
      <id>saturday06</id>
      <name>Isamu Mogi</name>
    </developer>
  </developers>
}
