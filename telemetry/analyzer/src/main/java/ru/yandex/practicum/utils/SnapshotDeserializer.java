package ru.yandex.practicum.utils;

import ru.yandex.practicum.kafka.telemetry.event.SnapshotAvro;

public class SnapshotDeserializer extends CommonAvroDeserializer<SnapshotAvro> {
    public SnapshotDeserializer() {
        super(SnapshotAvro.getClassSchema());
    }
}
