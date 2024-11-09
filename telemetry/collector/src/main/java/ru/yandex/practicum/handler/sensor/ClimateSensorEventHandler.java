package ru.yandex.practicum.handler.sensor;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.annotation.HandlerSensorEvent;
import ru.yandex.practicum.config.AppConfig;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.ClimateSensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.handler.CommonSensorEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.ClimateSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.CLIMATE_SENSOR_EVENT)
@Component
public class ClimateSensorEventHandler extends CommonSensorEventHandler<ClimateSensorAvro> {
    public ClimateSensorEventHandler(AppConfig config, KafkaEventProducer producer) {
        super(config, producer);
    }

    @Override
    protected ClimateSensorAvro mapToAvroObject(SensorEventProto event) {
        ClimateSensorEventProto sensorEvent = event.getClimateSensorEvent();
        return ClimateSensorAvro.newBuilder()
                .setTemperatureC(sensorEvent.getTemperatureC())
                .setHumidity(sensorEvent.getHumidity())
                .setCo2Level(sensorEvent.getCo2Level())
                .build();
    }
}