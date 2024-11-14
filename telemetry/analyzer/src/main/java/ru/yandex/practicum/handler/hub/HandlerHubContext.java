package ru.yandex.practicum.handler.hub;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Getter
public class HandlerHubContext {
    private final Map<String, HubHandler> context;

    public HandlerHubContext(Set<HubHandler> handlers) {
        context = handlers.stream()
                .collect(Collectors.toMap(HubHandler::getTypeOfPayload, h -> h));
    }
}
