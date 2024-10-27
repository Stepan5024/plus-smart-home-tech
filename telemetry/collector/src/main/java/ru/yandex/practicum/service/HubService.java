package ru.yandex.practicum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.hubs.HubEvent;
import ru.yandex.practicum.producer.KafkaProducerService;

@Service
public class HubService {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public HubService(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    public void processHubEvent(HubEvent event) {
        // Можно добавить дополнительную бизнес-логику или валидацию
        validateEvent(event);
        kafkaProducerService.sendHubEvent(event);
    }

    private void validateEvent(HubEvent event) {
        // Пример простой валидации (можно расширить при необходимости)
        if (event.getHubId() == null || event.getHubId().isEmpty()) {
            throw new IllegalArgumentException("Hub ID cannot be null or empty");
        }
        // Добавить любую дополнительную проверку при необходимости
    }
}