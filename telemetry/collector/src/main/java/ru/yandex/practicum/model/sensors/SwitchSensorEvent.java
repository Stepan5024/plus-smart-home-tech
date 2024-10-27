package ru.yandex.practicum.model.sensors;

public class SwitchSensorEvent extends SensorEvent {
    private boolean state;

    @Override
    public String getType() {
        return "SWITCH_SENSOR_EVENT";
    }
}
