package ru.yandex.practicum.handler;


import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.AppConfig;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public abstract class CommonHubEventHandler<T extends SpecificRecordBase> {
    private final AppConfig config;
    private final KafkaEventProducer producer;

    protected abstract T mapToAvroObject(HubEventProto event);

    public void handle(HubEventProto event) {
        T avroObject = mapToAvroObject(event);
        HubEventAvro hubEventAvro = HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(Instant.ofEpochSecond(event.getTimestamp().getSeconds(), event.getTimestamp().getNanos()))
                .setPayload(avroObject)
                .build();
        String topic = config.getTopicTelemetryHubs();
        producer.send(topic, event.getHubId(), hubEventAvro);
    }
}