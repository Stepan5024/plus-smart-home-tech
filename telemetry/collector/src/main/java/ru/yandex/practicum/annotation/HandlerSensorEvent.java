package ru.yandex.practicum.annotation;

import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HandlerSensorEvent {
    SensorEventProto.PayloadCase value();
}