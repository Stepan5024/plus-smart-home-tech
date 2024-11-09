package ru.yandex.practicum.model.hubs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.model.hubs.enums.ScenarioConditionOperation;
import ru.yandex.practicum.model.hubs.enums.ScenarioConditionType;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioCondition {
    String sensorId;
    ScenarioConditionType type;
    ScenarioConditionOperation operation;
    Object value;
}