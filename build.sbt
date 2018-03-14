name := """bX-mars"""

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)

scalaVersion := "2.11.7"
scalacOptions += "-language:postfixOps"

val testDependencies = Seq(
  ws % Test,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "org.mockito" % "mockito-core" % "1.10.19" % Test,
  "org.scalatestplus" %% "play" % "1.4.0" % Test
)

libraryDependencies ++= Seq(
  json,
  "org.scalaz" %% "scalaz-core" % "7.2.20" withSources(),
  "org.scalaz" %% "scalaz-effect" % "7.2.20" withSources(),
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0" withSources(),
  "commons-net" % "commons-net" % "3.6"
) ++ testDependencies


fork in run := true
connectInput in run := true

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
