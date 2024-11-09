package ru.yandex.practicum.handler.sensor;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.annotation.HandlerSensorEvent;
import ru.yandex.practicum.config.AppConfig;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.LightSensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.handler.CommonSensorEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.LightSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.LIGHT_SENSOR_EVENT)
@Component
public class LightSensorEventHandler extends CommonSensorEventHandler<LightSensorAvro> {
    public LightSensorEventHandler(AppConfig config, KafkaEventProducer producer) {
        super(config, producer);
    }

    @Override
    protected LightSensorAvro mapToAvroObject(SensorEventProto event) {
        LightSensorEventProto sensorEvent = event.getLightSensorEvent();
        return LightSensorAvro.newBuilder()
                .setLinkQuality(sensorEvent.getLinkQuality())
                .setLuminosity(sensorEvent.getLuminosity())
                .build();
    }
}