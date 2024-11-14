package ru.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.practicum.annotation.HandlerSensorEvent;
import ru.practicum.config.AppConfig;
import ru.practicum.config.KafkaEventProducer;
import ru.practicum.handler.CommonSensorEventHandler;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SwitchSensorEventProto;
import ru.yandex.practicum.kafka.telemetry.event.SwitchSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.SWITCH_SENSOR_EVENT)
@Component
public class SwitchSensorEventHandler extends CommonSensorEventHandler<SwitchSensorAvro> {
    public SwitchSensorEventHandler(AppConfig config, KafkaEventProducer producer) {
        super(config, producer);
    }

    @Override
    protected SwitchSensorAvro mapToAvroObject(SensorEventProto event) {
        SwitchSensorEventProto sensorEvent = event.getSwitchSensorEvent();
        return SwitchSensorAvro.newBuilder()
                .setState(sensorEvent.getState())
                .build();
    }
}
