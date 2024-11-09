package ru.practicum.repository;


import org.springframework.stereotype.Repository;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SnapshotAvro;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class AggregatorSnapshotRepository {
    private final Map<String, SnapshotAvro> snapshots = new HashMap<>();

    public Optional<SnapshotAvro> updateState(SensorEventAvro event) {
        SnapshotAvro currentSnapshot = snapshots.getOrDefault(event.getHubId(),
                SnapshotAvro.newBuilder()
                        .setHubId(event.getHubId())
                        .setTimestamp(Instant.now())
                        .setSensorState(new HashMap<>())
                        .build()
        );
        SensorStateAvro oldSensorState = currentSnapshot.getSensorState().get(event.getId());
        if (oldSensorState != null
                && oldSensorState.getTimestamp().isBefore(event.getTimestamp())
                && oldSensorState.getData().equals(event.getPayload())) {
            return Optional.empty();
        }
        SensorStateAvro newSensorState = new SensorStateAvro(event.getTimestamp(), event.getPayload());
        currentSnapshot.getSensorState().put(event.getId(), newSensorState);
        currentSnapshot.setTimestamp(event.getTimestamp());
        snapshots.put(event.getHubId(), currentSnapshot);
        return Optional.of(currentSnapshot);
    }
}