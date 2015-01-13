Option(System.getProperty("plugin.version")) match {
  case None => throw new IllegalStateException("The system property 'plugin.version' is not found")
  case Some(pluginVersion) => addSbtPlugin("jp.leafytree.sbt" % "sbt-phantomjs" % pluginVersion)
}
