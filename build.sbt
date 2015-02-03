name := "ScalaYara"

version       := "1.0"

scalaVersion  := "2.10.4"

libraryDependencies ++= Seq(
	"net.java.dev.jna" % "jna" % "4.0.0",
	"org.specs2" %% "specs2-core" % "2.4.15" % "test"
)