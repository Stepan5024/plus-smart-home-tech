package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.model.hubs.HubEvent;
import ru.yandex.practicum.producer.KafkaProducerService;

@Service
@AllArgsConstructor
public class HubService {

    private final KafkaProducerService kafkaProducerService;

    public void processHubEvent(HubEvent event) {
        kafkaProducerService.sendHubEvent(event);
    }

}