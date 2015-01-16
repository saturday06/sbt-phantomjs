package jp.leafytree.sbt

import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import sbt._
import Keys._

import collection.JavaConversions._

object PhantomJs extends AutoPlugin {
  object autoImport {
    lazy val installPhantomJs = taskKey[Unit]("Install PhantomJS")
    lazy val checkInstalledPhantomJs = taskKey[Unit]("Check installed PhantomJS")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    installPhantomJs := {
      val propertiesFile = PhantomJsInstaller.install(targetDirectory(baseDirectory.value))
      val properties = new Properties()
      IO.reader(propertiesFile, Charset.forName("UTF-8")){ properties.load(_) }
      properties.foreach { case (key, value) => {
        System.setProperty(key, value)
        javaOptions += f"-D${key}=${value}" // TODO: empty character?
      }}
    },

    checkInstalledPhantomJs := {
      val propertiesFile = targetDirectory(baseDirectory.value) / "phantomjs.properties"
      val properties = new Properties()
      IO.reader(propertiesFile, Charset.forName("UTF-8")){ properties.load(_) }
      val command = Array(properties.getProperty("phantomjs.binary.path"), "--version")
      val result = scala.sys.process.Process(command).run.exitValue
      if (result != 0) {
        sys.error("PhantomJS execution failure")
      }
    }
  )

  private def targetDirectory(baseDirectory: File): File = {
    baseDirectory / "target"
  }
}
