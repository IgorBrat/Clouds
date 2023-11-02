package com.lviv.iot.dto.assembler;

import com.lviv.iot.domain.EventEquipment;
import com.lviv.iot.dto.EventEquipmentDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class EventEquipmentDtoAssembler implements RepresentationModelAssembler<EventEquipment, EventEquipmentDto> {
    @Override
    public EventEquipmentDto toModel(EventEquipment entity) {
        return EventEquipmentDto.builder()
                .equipmentName(entity.getEquipment().getName())
                .quantity(entity.getQuantity())
                .build();
    }

    public CollectionModel<EventEquipmentDto> toCollectionModel(Iterable<? extends EventEquipment> entities, Link link) {
        CollectionModel<EventEquipmentDto> eventEquipmentDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        eventEquipmentDtos.add(link);
        return eventEquipmentDtos;
    }
}
