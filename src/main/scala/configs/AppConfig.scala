package configs

import com.typesafe.config.{Config, ConfigFactory}
import model.KafkaConsumerConfig

trait AppConfig {
    val config: Config = ConfigFactory.load

    private lazy val kBroker: String = config.getString("kafka.broker.bootstrap-servers")
    private lazy val kGroupId: String = config.getString("kafka.consumer.group-id")
    private lazy val kTopicIn: String = config.getString("kafka.consumer.topic-in")
    private lazy val kOffset: String = config.getString("kafka.consumer.offset-reset")
    private lazy val kNumStreamThread: String = config.getString("kafka.num-stream-thread")
    private lazy val kCleanup: Boolean = config.getBoolean("kafka.cleanup-state-store")
    private lazy val kMaxPollIntervalMs: String = config.getString("kafka.max-poll-interval-ms")
    private lazy val kSessionTimeoutMs: String = config.getString("kafka.session-timeout-ms")
    lazy val kConsumerConfig: KafkaConsumerConfig = KafkaConsumerConfig(
      kBroker,
      kGroupId,
      kTopicIn,
      kOffset,
      kNumStreamThread,
      kCleanup,
      kMaxPollIntervalMs,
      kSessionTimeoutMs)
    lazy val kTopicStatusCheck: String = config.getString("kafka.topicStatusCheck")
    lazy val kTopicStatusCheckResponse: String = config.getString("kafka.topicStatusCheckResponse")


}
