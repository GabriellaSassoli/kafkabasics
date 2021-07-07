name := "kafkaTraining"

version := "0.1"

scalaVersion := "2.13.6"
val kafkaVersion = "2.8.0"
val akkaHttpVersion = "10.2.4"
val akkaKafkaVersion = "2.1.0"
val akkaStreamVersion = "2.6.14"


libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % kafkaVersion,
  "org.slf4j" % "slf4j-simple" % "1.7.30",
  "com.typesafe.akka"            %% "akka-http-spray-json" % akkaHttpVersion,
  "io.spray" %%  "spray-json" % "1.3.6",
  "com.typesafe.akka"            %% "akka-stream-kafka"    % akkaKafkaVersion,
  "org.apache.kafka"             %% "kafka-streams-scala"  % kafkaVersion,
  "com.typesafe.akka"            %% "akka-stream"          % akkaStreamVersion

)