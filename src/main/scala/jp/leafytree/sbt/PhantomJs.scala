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
      PhantomJsInstaller.install(targetDirectory(baseDirectory.value)).foreach { case (key, value) => {
        System.setProperty(key, value)
        javaOptions += f"-D${key}=${value}" // TODO: empty character?
      }}
    },

    checkInstalledPhantomJs := {
      val properties = PhantomJsInstaller.install(targetDirectory(baseDirectory.value))
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

  def setup(baseDirectory: File): Seq[String] = {
    PhantomJsInstaller.install(targetDirectory(baseDirectory)).map { case (key, value) => {
      System.setProperty(key, value)
      f"-D${key}=${value}" // TODO: empty character?
    }}.toSeq
  }
}
