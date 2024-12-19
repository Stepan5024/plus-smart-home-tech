package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.payment.dto.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    @PostMapping
    public PaymentDto createPayment(@RequestBody @Valid OrderDto order) {
        log.info("Create payment for order: {}", order);
        return paymentService.createPayment(order);
    }

    @PostMapping("/totalCost")
    public Double totalCost(@RequestBody @Valid OrderDto order) {
        log.info("Total cost for order: {}", order);
        return paymentService.totalCost(order);
    }

    @PostMapping("/refund")
    public void refund(@RequestBody UUID orderId) {
        log.info("Refund payment for order: {}", orderId);
        paymentService.refund(orderId);
    }

    @PostMapping("/productCost")
    public Double productCost(@RequestBody @Valid OrderDto order) {
        log.info("Product cost for order: {}", order);
        return paymentService.productCost(order);
    }

    @PostMapping("/failed")
    public void failed(@RequestBody UUID orderId) {
        log.info("Failed payment for order: {}", orderId);
        paymentService.failed(orderId);
    }
}
