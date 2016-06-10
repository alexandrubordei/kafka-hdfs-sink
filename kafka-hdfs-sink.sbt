
// compile with sbt spDist
name := "kafka-consumer-serializer"

version := "1.0"

scalaVersion := "2.10.5"


resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"


libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.2.0"
libraryDependencies += "org.apache.spark" % "spark-streaming-kafka_2.10" % "1.6.1"
