package ru.yandex.practicum.model.sensors;

public class ClimateSensorEvent extends SensorEvent {
    private int temperatureC;
    private int humidity;
    private int co2Level;

    @Override
    public String getType() {
        return "CLIMATE_SENSOR_EVENT";
    }
}
