package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.NoPaymentFoundException;
import ru.yandex.practicum.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.order.dto.OrderDto;
import ru.yandex.practicum.order.feign.OrderClient;
import ru.yandex.practicum.payment.dto.PaymentDto;
import ru.yandex.practicum.payment.enums.PaymentStatus;
import ru.yandex.practicum.repository.PaymentRepository;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.shoppingStore.feign.ShoppingStoreClient;

import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentRepository paymentRepository;
    private final ShoppingStoreClient shoppingStoreClient;
    private final OrderClient orderClient;

    @Override
    public PaymentDto createPayment(OrderDto order) {
        checkOrder(order);
        Payment payment = Payment.builder()
                .orderId(order.getOrderId())
                .totalPayment(order.getTotalPrice())
                .deliveryTotal(order.getDeliveryPrice())
                .productsTotal(order.getProductPrice())
                .feeTotal(getTax(order.getTotalPrice()))
                .status(PaymentStatus.PENDING)
                .build();
        return paymentMapper.toPaymentDto(paymentRepository.save(payment));
    }


    @Override
    @Transactional(readOnly = true)
    public Double totalCost(OrderDto order) {
        if (order.getDeliveryPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Delivery price cannot be null");
        }
        return order.getProductPrice() + getTax(order.getProductPrice()) + order.getDeliveryPrice();
    }

    @Override
    public void refund(UUID uuid) {
        Payment payment = paymentRepository.findById(uuid).orElseThrow(
                () -> new NoPaymentFoundException("Payment not found"));
        payment.setStatus(PaymentStatus.SUCCESS);
        orderClient.payOrder(payment.getOrderId());
    }

    @Override
    @Transactional(readOnly = true)
    public Double productCost(OrderDto order) {
        double productCost = 0.0;
        Map<UUID, Long> products = order.getProducts();
        if (products == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Not enough info in order");
        }
        for (Map.Entry<UUID, Long> entry : products.entrySet()) {
            ProductDto product = shoppingStoreClient.getProduct(entry.getKey());
            productCost += product.getPrice() * entry.getValue();
        }
        return productCost;
    }

    @Override
    public void failed(UUID uuid) {
        Payment payment = paymentRepository.findById(uuid).orElseThrow(
                () -> new NoPaymentFoundException("Payment not found"));
        payment.setStatus(PaymentStatus.FAILED);
        orderClient.payOrderFailed(payment.getOrderId());
    }

    private void checkOrder(OrderDto order) {
        if (order.getDeliveryPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Delivery price is null");
        } else if (order.getProductPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Product price is null");
        } else if (order.getTotalPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("Total price is null");
        }
    }

    private double getTax(double totalPrice) {
        return totalPrice * 0.1;
    }
}
