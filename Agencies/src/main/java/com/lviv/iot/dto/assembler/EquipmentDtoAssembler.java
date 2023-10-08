package com.lviv.iot.dto.assembler;

import com.lviv.iot.domain.Equipment;
import com.lviv.iot.dto.EquipmentDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EquipmentDtoAssembler implements RepresentationModelAssembler<Equipment, EquipmentDto> {
    @Override
    public EquipmentDto toModel(Equipment entity) {
        EquipmentDto equipmentDto = EquipmentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .shopId(entity.getShopId())
                .build();
        Link selfLink = linkTo(methodOn(com.lviv.iot.controller.EquipmentController.class).getEquipment(equipmentDto.getId())).withSelfRel();
        equipmentDto.add(selfLink);
        return equipmentDto;
    }

    @Override
    public CollectionModel<EquipmentDto> toCollectionModel(Iterable<? extends Equipment> entities) {
        CollectionModel<EquipmentDto> equipmentDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(com.lviv.iot.controller.EquipmentController.class).getAllEquipments()).withSelfRel();
        equipmentDtos.add(selfLink);
        return equipmentDtos;
    }

    public CollectionModel<EquipmentDto> toCollectionModel(Iterable<? extends Equipment> entities, Link link) {
        CollectionModel<EquipmentDto> equipmentDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        equipmentDtos.add(link);
        return equipmentDtos;
    }
}
