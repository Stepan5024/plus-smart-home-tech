package ru.yandex.practicum.model.sensors;

public class LightSensorEvent extends SensorEvent {
    private int linkQuality;
    private int luminosity;

    @Override
    public String getType() {
        return "LIGHT_SENSOR_EVENT";
    }
}
