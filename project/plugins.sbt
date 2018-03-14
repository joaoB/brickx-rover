resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.6")

//code coverage plugin
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")

addSbtPlugin("com.iheart" % "sbt-play-swagger" % "0.5.2-PLAY2.4")