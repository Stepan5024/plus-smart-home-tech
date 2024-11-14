package ru.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.annotation.HandlerSensorEvent;
import ru.practicum.config.AppConfig;
import ru.practicum.config.KafkaEventProducer;
import ru.practicum.handler.CommonSensorEventHandler;
import ru.yandex.practicum.grpc.telemetry.event.MotionSensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.MotionSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.MOTION_SENSOR_EVENT)
@Component
public class MotionSensorEventHandler extends CommonSensorEventHandler<MotionSensorAvro> {
    public MotionSensorEventHandler(AppConfig config, KafkaEventProducer producer) {
        super(config, producer);
    }

    @Override
    protected MotionSensorAvro mapToAvroObject(SensorEventProto event) {
        MotionSensorEventProto sensorEvent = event.getMotionSensorEvent();
        return MotionSensorAvro.newBuilder()
                .setMotion(sensorEvent.getMotion())
                .setLinkQuality(sensorEvent.getLinkQuality())
                .setVoltage(sensorEvent.getVoltage())
                .build();
    }
}
