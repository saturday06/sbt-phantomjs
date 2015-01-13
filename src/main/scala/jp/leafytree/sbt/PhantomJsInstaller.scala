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
  def install(outputDirectory: File): File = {
    val propertiesFile = new File(outputDirectory, "phantomjs.properties")
    if (propertiesFile.exists()) {
      return propertiesFile
    }
    val mavenArchiveFile = getMavenArchiveFile().getOrElse {
      throw new RuntimeException("no maven!")
    }
    val unzipDir = IO.createTemporaryDirectory
    IO.unzip(mavenArchiveFile, unzipDir)
    val mavenHome = unzipDir.listFiles().filter(_.isDirectory).sortBy(_.getName).lastOption.getOrElse {
      throw new RuntimeException("no dir!")
    }
    new File(mavenHome, "bin" + File.separator + "mvn").setExecutable(true, true)

    IO.withTemporaryFile("pom-", ".xml") { pomFile =>
      val pomSource = Source.fromURL(getClass.getResource("pom.xml"))
      val pomString = try {
        pomSource.mkString
      } finally {
        pomSource.close()
      }
      IO.write(pomFile, pomString, Charset.forName("UTF-8"))

      val request = new DefaultInvocationRequest()
      request.setPomFile(pomFile)
      request.setGoals(java.util.Arrays.asList("install"))
      val properties = new Properties()
      properties.put("sbtphantomjs.outputDir", outputDirectory.getCanonicalPath())
      request.setProperties(properties)

      val invoker = new DefaultInvoker()
      invoker.setMavenHome(mavenHome)
      invoker.execute(request)

      if (!propertiesFile.exists()) {
        throw new RuntimeException("mvn failed")
      }
      return propertiesFile
    }
  }

  def getMavenArchiveFile(): Option[File] = {
    val ivySettings = new IvySettings()
    val resolver = new URLResolver()
    resolver.setM2compatible(true)
    resolver.setName("central")
    resolver.addArtifactPattern(
      "http://repo1.maven.org/maven2/[organisation]/[module]/[revision]/[artifact](-[revision]).[ext]")
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
          return Some(f.getLocalFile())
        }
      })
    }
    return None
  }
}
