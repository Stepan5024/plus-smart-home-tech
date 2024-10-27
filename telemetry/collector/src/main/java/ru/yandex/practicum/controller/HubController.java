package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.hubs.HubEvent;
import ru.yandex.practicum.service.HubService;


@RestController
@RequestMapping("/hubs")
public class HubController {

    private final HubService hubService;

    @Autowired
    public HubController(HubService hubService) {
        this.hubService = hubService;
    }

    @PostMapping
    public void collectHubEvent(@Valid @RequestBody HubEvent event) {
        hubService.processHubEvent(event);
    }
}