package ru.yandex.practicum.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.mapper.BookingMapper;
import ru.yandex.practicum.mapper.WarehouseMapper;
import ru.yandex.practicum.model.Booking;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.repository.BookingRepository;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.READ_COMMITTED)
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseMapper warehouseMapper;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public void newProductToWarehouse(NewProductInWarehouseRequestDto requestDto) {
        warehouseRepository.findById(requestDto.getProductId()).ifPresent(warehouse -> {
            throw new SpecifiedProductAlreadyInWarehouseException("Product already exists in warehouse");
        });
        Warehouse warehouse = warehouseMapper.toWarehouse(requestDto);
        warehouseRepository.save(warehouse);
    }

    @Override
    public void returnProductToWarehouse(Map<UUID, Long> products) {
        List<Warehouse> warehousesItems = warehouseRepository.findAllById(products.keySet());
        for (Warehouse warehouse : warehousesItems) {
            warehouse.setQuantity(warehouse.getQuantity() + products.get(warehouse.getProductId()));
        }
    }

    @Override
    public BookedProductsDto bookProduct(ShoppingCartDto shoppingCartDto) {
        Map<UUID, Long> products = shoppingCartDto.getProducts();
        List<Warehouse> productsInWarehouse = warehouseRepository.findAllById(products.keySet());
        productsInWarehouse.forEach(warehouse -> {
            if (warehouse.getQuantity() < products.get(warehouse.getProductId())) {
                throw new ProductInShoppingCartLowQuantityInWarehouse(
                        String.format("Product %s is sold out", warehouse.getProductId())
                );
            }
        });

        double deliveryVolume = productsInWarehouse.stream()
                .map(v -> v.getDimension().getDepth() * v.getDimension().getWidth()
                        * v.getDimension().getHeight())
                .mapToDouble(Double::doubleValue)
                .sum();

        double deliveryWeight = productsInWarehouse.stream()
                .map(Warehouse::getWeight)
                .mapToDouble(Double::doubleValue)
                .sum();

        boolean fragile = productsInWarehouse.stream()
                .anyMatch(Warehouse::isFragile);


        Booking newBooking = Booking.builder()
                .shoppingCartId(shoppingCartDto.getShoppingCartId())
                .deliveryVolume(deliveryVolume)
                .deliveryWeight(deliveryWeight)
                .fragile(fragile)
                .products(products)
                .build();
        Booking booking = bookingRepository.save(newBooking);
        return bookingMapper.toBookedProductsDto(booking);
    }

    @Override
    public BookedProductsDto assemblyProductForOrder(AssemblyProductForOrderFromShoppingCartDto assemblyProductDto) {
        Booking booking = bookingRepository.findById(assemblyProductDto.getShoppingCartId()).orElseThrow(
                () -> new RuntimeException(String.format("Shopping cart %s not found", assemblyProductDto.getShoppingCartId()))
        );


        Map<UUID, Long> productsInBooking = booking.getProducts();
        List<Warehouse> productsInWarehouse = warehouseRepository.findAllById(productsInBooking.keySet());
        productsInWarehouse.forEach(warehouse -> {
            if (warehouse.getQuantity() < productsInBooking.get(warehouse.getProductId())) {
                throw new ProductInShoppingCartLowQuantityInWarehouse(
                        String.format("Product %s is sold out", warehouse.getProductId()));
            }
        });

        for (Warehouse warehouse : productsInWarehouse) {
            warehouse.setQuantity(warehouse.getQuantity() - productsInBooking.get(warehouse.getProductId()));
        }
        booking.setOrderId(assemblyProductDto.getOrderId());
        return bookingMapper.toBookedProductsDto(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDto getAddress() {
        return AddressDto.builder()
                .country("Russia")
                .city("Krasnoyarsk")
                .street("Red street")
                .house("15")
                .flat("28")
                .build();
    }

    @Override
    public void addProductToWarehouse(AddProductToWarehouseRequest requestDto) {
        Warehouse warehouse = warehouseRepository.findById(requestDto.getProductId()).orElseThrow(
                () -> new NoSpecifiedProductInWarehouseException(String.format("Product %s not found", requestDto.getProductId()))
        );
        warehouse.setQuantity(warehouse.getQuantity() + requestDto.getQuantity());
    }

    @Override
    public void shippedToDelivery(ShippedToDeliveryRequest request) {
        Booking booking = bookingRepository.findByOrderId(request.getOrderId()).orElseThrow(
                () -> new NoSpecifiedProductInWarehouseException(String.format("Order %s not found", request.getOrderId()))
        );
        booking.setDeliveryId(request.getDeliveryId());
    }
}
