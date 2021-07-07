import configs.AppConfig
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import scala.collection.JavaConverters._
import java.util.Properties

object Consumer extends AppConfig{
  def main(args: Array[String]) = {
    val consumerProps: Properties = new Properties()
    consumerProps.put("group.id", "test")
    consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kConsumerConfig.bootstrapServer)
    consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    consumerProps.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")

    val consumer = new KafkaConsumer(consumerProps)
    val topics = List(kTopicStatusCheck)
    try {
      consumer subscribe topics.asJava
      while (true) {
        val records = consumer.poll(10)
        for (record <- records.asScala) {
          println("Topic: " + record.topic() +
            ",Key: " + record.key() +
            ",Value: " + record.value() +
            ", Offset: " + record.offset() +
            ", Partition: " + record.partition())
        }
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      consumer.close()
    }
  }

}
