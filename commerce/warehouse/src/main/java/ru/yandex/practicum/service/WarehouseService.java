package ru.yandex.practicum.service;

import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.*;

import java.util.Map;
import java.util.UUID;

public interface WarehouseService {
    void newProductToWarehouse(NewProductInWarehouseRequestDto requestDto);

    void returnProductToWarehouse(Map<UUID, Long> products);

    BookedProductsDto bookProduct(ShoppingCartDto shoppingCartDto);

    BookedProductsDto assemblyProductForOrder(AssemblyProductForOrderFromShoppingCartDto assemblyProductDto);

    AddressDto getAddress();

    void addProductToWarehouse(AddProductToWarehouseRequest requestDto);

    void shippedToDelivery(ShippedToDeliveryRequest request);
}
