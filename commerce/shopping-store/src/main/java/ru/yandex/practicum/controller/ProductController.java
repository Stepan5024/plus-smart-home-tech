package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.service.ProductService;
import ru.yandex.practicum.shoppingStore.dto.PageableDto;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.shoppingStore.dto.SetProductQuantityStateRequestDto;
import ru.yandex.practicum.shoppingStore.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-store")
@Slf4j
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProductsByCategory(@RequestParam ProductCategory category,
                                                  @Valid PageableDto pageableDto) {
        log.info("Get products by category");
        return productService.getProductsByCategory(category, pageableDto);
    }

    @PutMapping
    public ProductDto createProduct(@RequestBody @Valid ProductDto productDto) {
        log.info("Create product {}", productDto);
        return productService.createProduct(productDto);
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) {
        log.info("Update product {}", productDto);
        return productService.updateProduct(productDto);
    }

    @PostMapping("/removeProductFromStore")
    public Boolean deleteProduct(@RequestBody @NotNull UUID productId) {
        log.info("Delete product {}", productId);
        return productService.deleteProduct(productId);
    }

    @PostMapping("/quantityState")
    public Boolean updateProductQuantityState(@Valid SetProductQuantityStateRequestDto requestDto) {
        log.info("Update quantity state {}", requestDto);
        return productService.updateQuantityState(requestDto);
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable @NotNull UUID productId) {
        log.info("Get product by id: {}", productId);
        return productService.getProductById(productId);
    }
}
