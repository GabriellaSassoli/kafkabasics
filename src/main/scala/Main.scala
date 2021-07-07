
import akka.kafka.{CommitterSettings, ConsumerSettings, ProducerSettings}
import configs.AppConfig
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer, StickyAssignor}
import org.apache.kafka.common.serialization.{StringDeserializer, StringSerializer}
import akka.actor.ActorSystem
import akka.kafka.scaladsl.Producer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.errors.InvalidReplicationFactorException
import scala.collection.JavaConverters._
import org.apache.kafka.streams.scala.serialization.Serdes

import java.util.Properties
import scala.concurrent.ExecutionException
import scala.util.{Properties, Try}

object Producer extends AppConfig{
//  implicit val system: ActorSystem = ActorSystem()
//  private val committerSettings: CommitterSettings = CommitterSettings(system)

  def main(args: Array[String]) = {
    println("Hello, world")

//    //create Producer properties
    val producerProps:Properties = new Properties()
    producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kConsumerConfig.bootstrapServer)
    producerProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")
    producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")

    //create the producer
    val producer = new KafkaProducer[String, String](producerProps)

    // create a producer record
    val topic = kTopicStatusCheck

    //send data
    Try{
      for (i <- 0 to 15){
        val record = new ProducerRecord[String, String](topic, i.toString, s"This is the producer value $i")
        val metadata = producer.send(record)
        println(s"sent record key:${record.key()} and value:${record.value()}, partition ${metadata.get().partition()}, offset:${metadata.get().offset()}")
      }
    }.recover{
      case e: ExecutionException if e.getCause.getClass == classOf[InvalidReplicationFactorException] => println("Replication factor is invalid", e)
        System.exit(1)
      case e: Exception =>  println("Topics were not created. This is not fatal since another instance might have beaten us to it, continuing.", e)
    }
    producer.close()
    }




//  val producerSettings =
  //      ProducerSettings(system, new StringSerializer, new StringSerializer)
  //        .withBootstrapServers(kConsumerConfig.bootstrapServer)
  //        .withProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,Serdes.getClass.getName)
  //        .withProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Serdes.getClass.getName)
  //
  //    //create the producer
  //
  //    Producer.committableSink(producerSettings, committerSettings)
//    private val consumerSettings =
//      ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
//        .withBootstrapServers(kConsumerConfig.bootstrapServer)
//        .withGroupId(kConsumerConfig.groupId)
//        .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, kConsumerConfig.offset)
//        .withProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
//        .withProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000")
//        .withProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, kConsumerConfig.maxPollIntervalMs)
//        .withProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, kConsumerConfig.sessionTimeoutMs)
//        .withProperty(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, classOf[StickyAssignor].getCanonicalName)

}

