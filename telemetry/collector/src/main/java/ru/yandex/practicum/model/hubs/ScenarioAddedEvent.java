package ru.yandex.practicum.model.hubs;

import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.List;

public class ScenarioAddedEvent extends HubEvent {
    private String name;
    private List<ScenarioConditionAvro> conditions;
    private List<DeviceActionAvro> actions;

    @Override
    public String getType() {
        return "SCENARIO_ADDED_EVENT";
    }
}
