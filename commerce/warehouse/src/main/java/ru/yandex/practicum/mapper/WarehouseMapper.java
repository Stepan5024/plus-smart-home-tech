package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.model.Warehouse;
import ru.yandex.practicum.warehouse.dto.NewProductInWarehouseRequestDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseMapper {
    Warehouse toWarehouse(NewProductInWarehouseRequestDto newProductInWarehouseRequestDto);
}
