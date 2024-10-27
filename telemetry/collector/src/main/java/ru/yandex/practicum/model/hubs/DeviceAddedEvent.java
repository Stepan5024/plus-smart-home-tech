package ru.yandex.practicum.model.hubs;


import ru.yandex.practicum.kafka.telemetry.event.DeviceTypeAvro;

public class DeviceAddedEvent extends HubEvent {
    private String id;
    private DeviceTypeAvro type;

    @Override
    public String getType() {
        return "DEVICE_ADDED_EVENT";
    }
}
