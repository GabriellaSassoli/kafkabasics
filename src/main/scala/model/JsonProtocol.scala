package model


import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

case class KafkaConsumerConfig(bootstrapServer: String,
                               groupId: String,
                               topicIn: String,
                               offset: String,
                               numStreamThread: String,
                               cleanUp: Boolean,
                               maxPollIntervalMs: String,
                               sessionTimeoutMs: String)

trait JsonProtocol extends SprayJsonSupport with DefaultJsonProtocol{

  implicit val kafkaConsumerConfigFormat: RootJsonFormat[KafkaConsumerConfig] = jsonFormat8(KafkaConsumerConfig)

}
