package ru.practicum.model.hub;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.hub.enums.ScenarioConditionOperation;
import ru.practicum.model.hub.enums.ScenarioConditionType;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioCondition {
    String sensorId;
    ScenarioConditionType type;
    ScenarioConditionOperation operation;
    Object value;
}
