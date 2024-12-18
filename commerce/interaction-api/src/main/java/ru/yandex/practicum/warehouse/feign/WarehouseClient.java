package ru.yandex.practicum.warehouse.feign;

import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.AddressDto;
import ru.yandex.practicum.warehouse.dto.AssemblyProductForOrderFromShoppingCartDto;
import ru.yandex.practicum.warehouse.dto.ShippedToDeliveryRequest;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient {
    @PostMapping("/booking")
    BookedProductsDto bookingProduct(@RequestBody @Valid ShoppingCartDto shoppingCartDto);

    @PostMapping("/assembly")
    BookedProductsDto assemblyProductForOrder(@RequestBody @Valid AssemblyProductForOrderFromShoppingCartDto
                                                      assemblyProductDto);

    @PostMapping("/return")
    void returnProductToWarehouse(@RequestBody Map<UUID, Long> products);

    @GetMapping("/address")
    AddressDto getAddress();

    @PostMapping("/shipped")
    void shippedToDelivery(ShippedToDeliveryRequest request);
}
