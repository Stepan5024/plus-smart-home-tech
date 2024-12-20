package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.stereotype.Service;
import ru.practicum.config.AppConfig;
import ru.practicum.repository.AggregatorSnapshotRepository;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SnapshotAvro;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AggregationStarter {
    private final AggregatorSnapshotRepository aggregatorSnapshotRepository;
    private final KafkaProducer<String, SpecificRecordBase> producer;
    private final KafkaConsumer<String, SensorEventAvro> consumer;
    private final AppConfig appConfig;

    public void start() {

        try {
            consumer.subscribe(List.of(appConfig.getInTopic()));
            Runtime.getRuntime().addShutdownHook(new Thread(consumer::wakeup));
            while (true) {
                ConsumerRecords<String, SensorEventAvro> records = consumer.poll(Duration.ofMillis(500));
                if (records.isEmpty()) continue;

                for (ConsumerRecord<String, SensorEventAvro> record : records) {

                    log.info("topic = {}, partition = {}, offset = {}, record = {}\n",
                            record.topic(), record.partition(), record.offset(), record.value());

                    Optional<SnapshotAvro> snapshotAvro = aggregatorSnapshotRepository.updateState(record.value());
                    if (snapshotAvro.isPresent()) {
                        ProducerRecord<String, SpecificRecordBase> producerRecord =
                                new ProducerRecord<>(appConfig.getOutTopic(),
                                        snapshotAvro.get().getHubId(), snapshotAvro.get());
                        producer.send(producerRecord, (metadata, exception) -> {
                            if (exception != null) {
                                log.error("Error sending to Kafka, topic: {}, ",
                                        appConfig.getOutTopic(), exception);
                                throw new RuntimeException("Exception occurs by Kafka producer", exception);
                            }
                            log.info("Message sent to Kafka, topic: {}, partition: {}, offset: {}",
                                    metadata.topic(), metadata.partition(), metadata.offset());
                        });
                    }
                }
                consumer.commitSync();
            }
        } catch (WakeupException ignored) {

        } catch (Exception e) {
            log.error("Error sending to Kafka", e);
        } finally {
            log.info("Consumer stopped");
            consumer.close();
            log.info("Producer stopped");
            producer.close();
        }
    }
}