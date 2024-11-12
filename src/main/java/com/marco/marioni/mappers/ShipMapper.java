package com.marco.marioni.mappers;

import com.marco.marioni.domain.dtos.ShipDTO;
import com.marco.marioni.domain.entities.ShipEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShipMapper {

    ShipEntity toEntity(ShipDTO shipDTO);

    ShipDTO toDTO(ShipEntity shipEntity);

    List<ShipDTO> toDTOList(List<ShipEntity> shipEntities);
}
