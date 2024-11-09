package ru.yandex.practicum.handler.hub;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.annotation.HandlerHubEvent;
import ru.yandex.practicum.config.AppConfig;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.handler.CommonHubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.List;

@Component
@HandlerHubEvent(HubEventProto.PayloadCase.SCENARIO_ADDED)
public class HubScenarioAddedEventHandler extends CommonHubEventHandler<ScenarioAddedEventAvro> {
    public HubScenarioAddedEventHandler(AppConfig config, KafkaEventProducer producer) {
        super(config, producer);
    }

    @Override
    protected ScenarioAddedEventAvro mapToAvroObject(HubEventProto event) {
        ScenarioAddedEventProto scenarioAddedEvent = event.getScenarioAdded();

        return ScenarioAddedEventAvro.newBuilder()
                .setName(scenarioAddedEvent.getName())
                .setActions(getDeviceActionAvroList(scenarioAddedEvent))
                .setConditions(getScenarioConditionAvroList(scenarioAddedEvent))
                .build();
    }

    private List<ScenarioConditionAvro> getScenarioConditionAvroList(ScenarioAddedEventProto scenarioAddedEvent) {
        return scenarioAddedEvent.getConditionList().stream()
                .map(c -> ScenarioConditionAvro.newBuilder()
                        .setSensorId(c.getSensorId())
                        .setType(ConditionTypeAvro.valueOf(c.getType().name()))
                        .setOperation(ConditionOperationAvro.valueOf(c.getOperation().name()))
                        .setValue(c.hasBoolValue() ? c.getBoolValue() : c.getIntValue())
                        .build())
                .toList();
    }

    private List<DeviceActionAvro> getDeviceActionAvroList(ScenarioAddedEventProto scenarioAddedEvent) {
        return scenarioAddedEvent.getActionList().stream()
                .map(a -> DeviceActionAvro.newBuilder()
                        .setSensorId(a.getSensorId())
                        .setType(ActionTypeAvro.valueOf(a.getType().name()))
                        .setValue(a.getValue())
                        .build())
                .toList();
    }
}