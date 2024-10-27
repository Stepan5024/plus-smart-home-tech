package ru.yandex.practicum.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.sensors.SensorEvent;
import ru.yandex.practicum.producer.KafkaProducerService;

@Service
public class SensorService {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public SensorService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public void processSensorEvent(SensorEvent event) {
        // Дополнительная обработка и валидация
        validateEvent(event);
        kafkaProducerService.sendSensorEvent(event);
    }

    private void validateEvent(SensorEvent event) {
        // Пример простой валидации
        if (event.getId() == null || event.getId().isEmpty()) {
            throw new IllegalArgumentException("Sensor ID cannot be null or empty");
        }
        if (event.getHubId() == null || event.getHubId().isEmpty()) {
            throw new IllegalArgumentException("Hub ID cannot be null or empty");
        }
        // Дополнительная проверка при необходимости
    }
}