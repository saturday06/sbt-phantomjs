package jp.leafytree.sbt

import java.nio.charset.Charset
import java.util.Properties

import sbt._
import Keys._

import collection.JavaConversions._

@deprecated("Please use `PhantomJs`")
object SbtPhantomJs extends AutoPlugin {
  override def requires = PhantomJs
  override def trigger = allRequirements
}
