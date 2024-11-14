package ru.practicum.annotation;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.practicum.handler.CommonHubEventHandler;
import ru.practicum.handler.CommonSensorEventHandler;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HandlerContext {
    private final BeanSorter beanSorter;
    @Getter
    private Map<Enum<?>, CommonHubEventHandler<? extends SpecificRecordBase>> hubEventHandlers = new HashMap<>();
    @Getter
    private Map<Enum<?>, CommonSensorEventHandler<? extends SpecificRecordBase>> sensorEventHandlers = new HashMap<>();

    @PostConstruct
    public void init() {
        hubEventHandlers = beanSorter.getAnnotatedBeans(HandlerHubEvent.class).stream()
                .map(h -> (CommonHubEventHandler<?>) h)
                .collect(Collectors.toMap(HandlerContext::getValueHub, h -> h));

        sensorEventHandlers = beanSorter.getAnnotatedBeans(HandlerSensorEvent.class).stream()
                .map(h -> (CommonSensorEventHandler<?>) h)
                .collect(Collectors.toMap(HandlerContext::getValueSensor, h -> h));
    }

    private static HubEventProto.PayloadCase getValueHub(CommonHubEventHandler<?> handler) {
        HandlerHubEvent handlerAnnotation = handler.getClass().getAnnotation(HandlerHubEvent.class);
        if (handlerAnnotation == null) {
            throw new IllegalArgumentException("No annotation found for " + handler.getClass().getName());
        }
        return handlerAnnotation.value();
    }

    private static SensorEventProto.PayloadCase getValueSensor(CommonSensorEventHandler<?> handler) {
        HandlerSensorEvent handlerAnnotation = handler.getClass().getAnnotation(HandlerSensorEvent.class);
        if (handlerAnnotation == null) {
            throw new IllegalArgumentException("No annotation found for " + handler.getClass().getName());
        }
        return handlerAnnotation.value();
    }
}
