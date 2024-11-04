package ru.yandex.practicum.model.hubs;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioRemovedEvent extends HubEvent {
    String name;

    @Override
    public String getType() {
        return "SCENARIO_REMOVED_EVENT";
    }
}
