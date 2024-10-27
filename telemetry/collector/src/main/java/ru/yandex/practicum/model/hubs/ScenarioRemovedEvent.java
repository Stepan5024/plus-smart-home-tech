package ru.yandex.practicum.model.hubs;

public class ScenarioRemovedEvent extends HubEvent {
    private String name;

    @Override
    public String getType() {
        return "SCENARIO_REMOVED_EVENT";
    }
}
