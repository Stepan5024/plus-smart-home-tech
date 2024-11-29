package ru.practicum.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.utils.CommonAvroSerializer;

import java.util.Properties;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class AppConfig {
    @Value("${collector.topic.sensor}")
    String topicTelemetrySensors;
    @Value("${collector.topic.hub}")
    String topicTelemetryHubs;
    @Value("${collector.kafka.server}")
    String kafkaBootstrapServers;

    @Bean
    public KafkaProducer<String, SpecificRecordBase> getKafkaProducer() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CommonAvroSerializer.class);
        props.put(ProducerConfig.LINGER_MS_CONFIG, 2_000);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10_000);
        return new KafkaProducer<>(props);
    }

}
