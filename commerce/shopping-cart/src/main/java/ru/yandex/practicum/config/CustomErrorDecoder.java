package ru.yandex.practicum.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.yandex.practicum.warehouse.feignException.InternalServerErrorException;
import ru.yandex.practicum.warehouse.feignException.ProductInShoppingCartLowQuantityInWarehouse;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 400 -> new ProductInShoppingCartLowQuantityInWarehouse("Product is sold out " + response.reason());
            case 500 -> new InternalServerErrorException("Internal server error " + response.reason());
            default -> defaultDecoder.decode(s, response);
        };
    }
}
