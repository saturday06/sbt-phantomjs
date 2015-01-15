package jp.leafytree.sbt

import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset
import java.util.Properties

import sbt._
import Keys._

import collection.JavaConversions._

class PhantomJsPlugin extends AutoPlugin {
  object autoImport {
    lazy val installPhantomJs = taskKey[Unit]("Install PhantomJS")
    lazy val checkInstalledPhantomJs = taskKey[Unit]("Check installed PhantomJS")
  }
  import autoImport._

  override lazy val projectSettings = Seq(
    installPhantomJs := {
      val propertiesFile = PhantomJsInstaller.install(baseDirectory.value / "target")
      val properties = new Properties()
      IO.reader(propertiesFile, Charset.forName("UTF-8")){ properties.load(_) }
      properties.foreach { case (key, value) => {
        System.setProperty(key, value)
        javaOptions += f"-D${key}=${value}" // TODO: empty character?
      }}
    },

    checkInstalledPhantomJs := {
      val propertiesFile = baseDirectory.value / "target" / "phantomjs.properties"
      val properties = new Properties()
      IO.reader(propertiesFile, Charset.forName("UTF-8")){ properties.load(_) }
      val command = Array(properties.getProperty("phantomjs.binary.path"), "--version")
      val result = scala.sys.process.Process(command).run.exitValue
      if (result != 0) {
        sys.error("PhantomJS execution failure")
      }
    }
  )
}
