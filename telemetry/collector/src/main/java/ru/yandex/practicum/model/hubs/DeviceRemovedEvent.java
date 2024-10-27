package ru.yandex.practicum.model.hubs;

public class DeviceRemovedEvent extends HubEvent {
    private String id;

    @Override
    public String getType() {
        return "DEVICE_REMOVED_EVENT";
    }
}
