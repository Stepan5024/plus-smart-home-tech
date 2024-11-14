package ru.practicum.handler.hub;

import org.springframework.stereotype.Component;
import ru.practicum.annotation.HandlerHubEvent;
import ru.practicum.config.AppConfig;
import ru.practicum.config.KafkaEventProducer;
import ru.practicum.handler.CommonHubEventHandler;
import ru.yandex.practicum.grpc.telemetry.event.DeviceRemovedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;

@Component
@HandlerHubEvent(HubEventProto.PayloadCase.DEVICE_REMOVED)
public class HubDeviceRemovedEventHandler extends CommonHubEventHandler<DeviceRemovedEventAvro> {
    public HubDeviceRemovedEventHandler(AppConfig config, KafkaEventProducer producer) {
        super(config, producer);
    }

    @Override
    protected DeviceRemovedEventAvro mapToAvroObject(HubEventProto event) {
        DeviceRemovedEventProto removedEvent = event.getDeviceRemoved();
        return DeviceRemovedEventAvro.newBuilder()
                .setId(removedEvent.getId())
                .build();
    }
}
