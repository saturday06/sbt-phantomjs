package jp.leafytree.sbt

import java.io.File
import java.nio.charset.Charset
import java.util.Properties

import sbt._
import Keys._

import collection.JavaConversions._

object PhantomJs extends AutoPlugin {
  object autoImport {
    lazy val phantomJsBinaryVersion = settingKey[String]("PhantomJS version")
    lazy val phantomJsMavenPluginVersion = settingKey[String]("phantomjs-maven-plugin version")
    lazy val installPhantomJs = taskKey[Unit]("Install PhantomJS")
    lazy val checkInstalledPhantomJs = taskKey[Unit]("Check installed PhantomJS")
  }

  import autoImport._

  override lazy val projectSettings = Seq(
    phantomJsBinaryVersion := "2.0.0",
    phantomJsMavenPluginVersion := "0.7",
    installPhantomJs := {
      PhantomJsInstaller.install(
        targetDirectory(baseDirectory.value),
        phantomJsBinaryVersion.value,
        phantomJsMavenPluginVersion.value
      ).foreach {
        case (key, value) =>
          System.setProperty(key, value)
          javaOptions += f"-D${key}=${value}" // TODO: empty character?
      }
    },

    checkInstalledPhantomJs := {
      val properties = PhantomJsInstaller.install(
        targetDirectory(baseDirectory.value),
        phantomJsBinaryVersion.value,
        phantomJsMavenPluginVersion.value
      )
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

  def setup(
    baseDirectory: File,
    phantomJsVersion: String = "2.0.0",
    phantomJsMavenPluginVersion: String = "0.7"
  ): Seq[String] = {
    PhantomJsInstaller.install(
      targetDirectory(baseDirectory),
      phantomJsVersion,
      phantomJsMavenPluginVersion
    ).map {
      case (key, value) =>
        System.setProperty(key, value)
        f"-D${key}=${value}" // TODO: empty character?
    }.toSeq
  }
}
