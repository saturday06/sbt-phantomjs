package jp.leafytree.sbt

import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import org.apache.ivy.Ivy
import org.apache.ivy.core.resolve.ResolveOptions
import org.apache.ivy.core.settings.IvySettings
import org.apache.ivy.plugins.resolver.URLResolver
import org.apache.maven.shared.invoker._
import sbt.IO

import scala.io.Source

object PhantomJsInstaller {
  def executeMaven(
    mavenHome: File,
    outputDirectory: File,
    phantomJsVersion: String,
    phantomJsMavenPluginVersion: String
  ): Unit = {
    val pomSource = Source.fromURL(getClass.getResource("pom.xml"))
    val pomString = try {
      pomSource.mkString
    } finally {
      pomSource.close()
    }

    IO.withTemporaryFile("pom-", ".xml") { pomFile =>
      IO.write(pomFile, pomString, Charset.forName("UTF-8"))

      val request = new DefaultInvocationRequest()
      request.setPomFile(pomFile)
      request.setGoals(java.util.Arrays.asList("package"))
      val properties = new Properties()
      properties.put("sbtphantomjs.outputDir", outputDirectory.getCanonicalPath())
      properties.put("sbtphantomjs.phantomJsVersion", phantomJsVersion)
      properties.put("sbtphantomjs.mavenPluginVersion", phantomJsMavenPluginVersion)
      request.setProperties(properties)

      val invoker = new DefaultInvoker()
      invoker.setMavenHome(mavenHome)
      invoker.execute(request)
    }
  }

  def installMaven(mavenArchiveFile: File): File = {
    val unzipDir = IO.createTemporaryDirectory
    IO.unzip(mavenArchiveFile, unzipDir)
    val mavenHome = unzipDir.listFiles().filter(_.isDirectory).sortBy(_.getName).lastOption.getOrElse {
      sys.error("no dir!")
    }
    new File(mavenHome, "bin" + File.separator + "mvn").setExecutable(true, true)
    mavenHome
  }

  def readProperties(file: File): Properties = {
    val properties = new Properties()
    IO.reader(file, Charset.forName("UTF-8")){ properties.load(_) }
    properties
  }

  def install(
    outputDirectory: File,
    phantomJsVersion: String,
    phantomJsMavenPluginVersion: String
  ): Properties = {
    val propertiesFile = new File(outputDirectory, "phantomjs.properties")
    if (propertiesFile.exists()) {
      readProperties(propertiesFile)
    } else {
      executeMaven(installMaven(
        mavenArchiveFile()),
        outputDirectory,
        phantomJsVersion,
        phantomJsMavenPluginVersion
      )

      if (!propertiesFile.exists()) {
        sys.error("mvn failed")
      }
      readProperties(propertiesFile)
    }
  }

  def mavenArchiveFile(): File = {
    val ivySettings = new IvySettings()
    val resolver = new URLResolver()
    resolver.setM2compatible(true)
    resolver.setName("central")
    resolver.addArtifactPattern(
      "http://repo1.maven.org/maven2/[organisation]/[module]/[revision]/[artifact]-[revision](-[classifier]).[ext]")
    ivySettings.addResolver(resolver)
    ivySettings.setDefaultResolver(resolver.getName())
    val ivy = Ivy.newInstance(ivySettings)
    IO.withTemporaryFile("ivy-", ".xml") { ivyFile =>
      val ivySource = Source.fromURL(getClass.getResource("ivy.xml"))
      val ivyString = try {
        ivySource.mkString
      } finally {
        ivySource.close()
      }
      IO.write(ivyFile, ivyString, Charset.forName("UTF-8"))
      val resolveOptions = new ResolveOptions().setConfs(Array[String]("default"))
      val report = ivy.resolve(ivyFile.toURI.toURL, resolveOptions)
      report.getAllArtifactsReports().foreach( f => {
        val artifact = f.getArtifact()
        if (artifact.getExt() == "zip"
          && artifact.getName() == "apache-maven"
          && artifact.getModuleRevisionId().getOrganisation == "org.apache.maven") {
          return f.getLocalFile()
        }
      })
    }
    sys.error("no maven!")
  }
}
