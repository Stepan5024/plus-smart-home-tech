package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.service.ShoppingCartService;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;
import ru.yandex.practicum.shoppingCart.dto.ChangeProductQuantityRequestDto;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@Slf4j
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public ShoppingCartDto getShoppingCart(@RequestParam String username) {
        log.info("Get shopping cart for username {}", username);
        return shoppingCartService.getShoppingCart(username);
    }

    @PutMapping
    public ShoppingCartDto addProductsToCart(@RequestParam String username,
                                             @RequestBody Map<UUID, Long> request) {
        log.info("Add products to shopping cart for username {}", username);
        return shoppingCartService.addProductsToCart(username, request);
    }

    @DeleteMapping
    public void deactivateSoppingCart(@RequestParam String username) {
        log.info("Delete shopping cart for username {}", username);
        shoppingCartService.deactivateShoppingCart(username);
    }

    @PostMapping("/remove")
    public ShoppingCartDto changeShoppingCart(@RequestParam String username,
                                              @RequestBody Map<UUID, Long> request) {
        log.info("Change shopping cart for username {}", username);
        return shoppingCartService.changeShoppingCart(username, request);
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeQuantityShoppingCart(@RequestParam String username,
                                                      @RequestBody @Valid ChangeProductQuantityRequestDto requestDto) {
        log.info("Change quantity shopping cart for username {}", username);
        return shoppingCartService.changeQuantityShoppingCart(username, requestDto);
    }

    @PostMapping("/booking")
    public BookedProductsDto bookingProducts(@RequestParam String username) {
        log.info("Booking shopping cart for username {}", username);
        return shoppingCartService.bookingProducts(username);
    }
}
