ThisBuild / organization := "com.eed3si9n.manifesto"
ThisBuild / scalaVersion := "3.3.4"
name := "manifesto"
libraryDependencies += "com.eed3si9n.verify" %% "verify" % "1.0.0" % Test
testFrameworks += new TestFramework("verify.runner.Framework")

ThisBuild / organizationName := "eed3si9n"
ThisBuild / organizationHomepage := Some(url("http://eed3si9n.com/"))
ThisBuild / homepage := Some(url("https://github.com/eed3si9n/manifesto"))
ThisBuild / scmInfo := Some(
  ScmInfo(url("https://github.com/eed3si9n/manifesto"), "git@github.com:eed3si9n/manifesto.git")
)
ThisBuild / developers := List(
  Developer("eed3si9n", "Eugene Yokota", "@eed3si9n", url("https://github.com/eed3si9n"))
)
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / description := "Manifesto is a Scala 3 reimplementation of Manifest"
ThisBuild / licenses := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
