name := "CSYE7200_FinalProject"

version := "0.1.0"

scalaVersion := "2.11.8"

resolvers += "Maven central" at "http://repo1.maven.org/maven2/"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

libraryDependencies += "io.spray" %%  "spray-json" % "1.3.5"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  "org.twitter4j" % "twitter4j-stream" % "3.0.5",
  "oauth.signpost" % "signpost-core" % "1.2",
  "oauth.signpost" % "signpost-commonshttp4" % "1.2",
  "org.apache.httpcomponents" % "httpclient" % "4.5",
  "org.apache.httpcomponents" % "httpcore" % "4.4.6",
  "org.apache.commons" % "commons-io" % "1.3.2",
  "org.apache.spark" %% "spark-core" % "2.1.0",
  "org.apache.spark" % "spark-streaming_2.11" % "2.1.0",
  "org.apache.bahir" %% "spark-streaming-twitter" % "2.1.0")

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % "2.3.0"

libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.6.0"classifier "models"
