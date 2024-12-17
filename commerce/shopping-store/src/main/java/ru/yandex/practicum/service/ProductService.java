package ru.yandex.practicum.service;

import ru.yandex.practicum.shoppingStore.dto.PageableDto;
import ru.yandex.practicum.shoppingStore.dto.ProductDto;
import ru.yandex.practicum.shoppingStore.dto.SetProductQuantityStateRequestDto;
import ru.yandex.practicum.shoppingStore.enums.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductDto> getProductsByCategory(ProductCategory category, PageableDto pageableDto);

    ProductDto createProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    boolean deleteProduct(UUID productId);

    boolean updateQuantityState(SetProductQuantityStateRequestDto requestDto);

    ProductDto getProductById(UUID productId);
}
