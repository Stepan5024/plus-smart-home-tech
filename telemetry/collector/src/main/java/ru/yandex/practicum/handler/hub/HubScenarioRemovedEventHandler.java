package ru.yandex.practicum.handler.hub;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.annotation.HandlerHubEvent;
import ru.yandex.practicum.config.AppConfig;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioRemovedEventProto;
import ru.yandex.practicum.handler.CommonHubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@Component
@HandlerHubEvent(HubEventProto.PayloadCase.SCENARIO_REMOVED)
public class HubScenarioRemovedEventHandler extends CommonHubEventHandler<ScenarioRemovedEventAvro> {
    public HubScenarioRemovedEventHandler(AppConfig config, KafkaEventProducer producer) {
        super(config, producer);
    }

    @Override
    protected ScenarioRemovedEventAvro mapToAvroObject(HubEventProto event) {
        ScenarioRemovedEventProto removedEvent = event.getScenarioRemoved();
        return ScenarioRemovedEventAvro.newBuilder()
                .setName(removedEvent.getName())
                .build();
    }
}