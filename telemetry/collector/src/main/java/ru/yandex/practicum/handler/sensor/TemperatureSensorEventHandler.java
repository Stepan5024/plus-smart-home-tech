package ru.yandex.practicum.handler.sensor;


import org.springframework.stereotype.Component;
import ru.yandex.practicum.annotation.HandlerSensorEvent;
import ru.yandex.practicum.config.AppConfig;
import ru.yandex.practicum.config.KafkaEventProducer;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import ru.yandex.practicum.grpc.telemetry.event.TemperatureSensorEventProto;
import ru.yandex.practicum.handler.CommonSensorEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.TemperatureSensorAvro;

@HandlerSensorEvent(SensorEventProto.PayloadCase.TEMPERATURE_SENSOR_EVENT)
@Component
public class TemperatureSensorEventHandler extends CommonSensorEventHandler<TemperatureSensorAvro> {
    public TemperatureSensorEventHandler(AppConfig config, KafkaEventProducer producer) {
        super(config, producer);
    }

    @Override
    protected TemperatureSensorAvro mapToAvroObject(SensorEventProto event) {
        TemperatureSensorEventProto sensorEvent = event.getTemperatureSensorEvent();
        return TemperatureSensorAvro.newBuilder()
                .setTemperatureC(sensorEvent.getTemperatureC())
                .setTemperatureF(sensorEvent.getTemperatureF())
                .build();
    }
}