package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.yandex.practicum.delivery.dto.DeliveryDto;
import ru.yandex.practicum.model.Delivery;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeliveryMapper {
    DeliveryDto toDto(Delivery delivery);

    Delivery fromDto(DeliveryDto deliveryDto);
}
