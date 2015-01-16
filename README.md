# sbt-phantomjs  [![Build Status](https://travis-ci.org/saturday06/sbt-phantomjs.png?branch=master)](https://travis-ci.org/saturday06/sbt-phantomjs)

An automated PhantomJS Installer for sbt

## Setup

`project/plugins.sbt`
```scala
addSbtPlugin("jp.leafytree.sbt" % "sbt-phantomjs" % "0.1.4")
```

`build.sbt`
```scala
// Enable the plugin
val myProject = (project in file(".")).enablePlugins(PhantomJs)

// Install PhantomJS and add system properties definition for Selenium PhantomJS Driver to javaOptions
javaOptions ++= PhantomJs.setup(baseDirectory.value)
```

## Samples
- [Play Framework + specs2](https://github.com/saturday06/sbt-phantomjs/tree/master/sample/playframework)
- [specs2](https://github.com/saturday06/sbt-phantomjs/tree/master/sample/specs2)
- [ScalaTest](https://github.com/saturday06/sbt-phantomjs/tree/master/sample/scalatest)

## Tasks

### installPhantomJs task

```bash
$ sbt installPhantomJs
```

- Install PhantomJS to `target/phantomjs/`
- Write properties for Selenium PhantomJS Driver to ...
  - `target/phantomjs.properties` file
  - System Property using `System.setProperty()`
  - `javaOptions` of sbt
