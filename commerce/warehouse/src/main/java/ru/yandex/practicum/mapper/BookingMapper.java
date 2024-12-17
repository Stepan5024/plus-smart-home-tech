package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.model.Booking;
import ru.yandex.practicum.shoppingCart.dto.BookedProductsDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    BookedProductsDto toBookedProductsDto(Booking booking);
}
