package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.shoppingCart.dto.ShoppingCartDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShoppingCartMapper {
    ShoppingCartDto toShoppingCartDto(ShoppingCart shoppingCart);
}
