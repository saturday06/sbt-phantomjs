resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")

Option(System.getProperty("plugin.version")) match {
  case None => throw new IllegalStateException("The system property 'plugin.version' is not found")
  case Some(pluginVersion) => addSbtPlugin("jp.leafytree.sbt" % "sbt-phantomjs" % pluginVersion)
}
