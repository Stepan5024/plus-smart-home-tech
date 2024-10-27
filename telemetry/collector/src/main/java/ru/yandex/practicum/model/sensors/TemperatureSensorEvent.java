package ru.yandex.practicum.model.sensors;

public class TemperatureSensorEvent extends SensorEvent {
    private int temperatureC;
    private int temperatureF;

    @Override
    public String getType() {
        return "TEMPERATURE_SENSOR_EVENT";
    }
}
