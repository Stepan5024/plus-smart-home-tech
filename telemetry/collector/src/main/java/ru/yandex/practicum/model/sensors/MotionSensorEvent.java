package ru.yandex.practicum.model.sensors;

public class MotionSensorEvent extends SensorEvent {
    private int linkQuality;
    private boolean motion;
    private int voltage;

    @Override
    public String getType() {
        return "MOTION_SENSOR_EVENT";
    }
}
