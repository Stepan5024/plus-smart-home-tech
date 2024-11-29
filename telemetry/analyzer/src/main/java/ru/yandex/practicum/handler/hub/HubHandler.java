package ru.yandex.practicum.handler.hub;

import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubHandler {
    String getTypeOfPayload();

    void handle(HubEventAvro hubEventAvro);
}
