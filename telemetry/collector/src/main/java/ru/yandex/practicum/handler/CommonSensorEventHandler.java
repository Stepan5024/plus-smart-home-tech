package ru.yandex.practicum.handler;


import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.AppConfig;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public abstract class CommonSensorEventHandler<T extends SpecificRecordBase> {
    private final AppConfig config;
    private final KafkaEventProducer producer;

    protected abstract T mapToAvroObject(SensorEventProto event);

    public void handle(SensorEventProto event) {
        T avroObject = mapToAvroObject(event);
        SensorEventAvro sensorEventAvro = SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(
                        event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .setPayload(avroObject)
                .build();
        String topic = config.getTopicTelemetrySensors();
        producer.send(topic, event.getHubId(), sensorEventAvro);
    }
}