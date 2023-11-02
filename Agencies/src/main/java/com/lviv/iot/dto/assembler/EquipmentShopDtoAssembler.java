package com.lviv.iot.dto.assembler;

import com.lviv.iot.domain.EquipmentShop;
import com.lviv.iot.dto.EquipmentShopDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EquipmentShopDtoAssembler implements RepresentationModelAssembler<EquipmentShop, EquipmentShopDto> {
    @Override
    public EquipmentShopDto toModel(EquipmentShop entity) {
        EquipmentShopDto equipmentShopDto = EquipmentShopDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
        Link selfLink = linkTo(methodOn(com.lviv.iot.controller.EquipmentShopController.class).getEquipmentShop(equipmentShopDto.getId())).withSelfRel();
        equipmentShopDto.add(selfLink);
        return equipmentShopDto;
    }

    @Override
    public CollectionModel<EquipmentShopDto> toCollectionModel(Iterable<? extends EquipmentShop> entities) {
        CollectionModel<EquipmentShopDto> equipmentShopDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(com.lviv.iot.controller.EquipmentShopController.class).getAllEquipmentShops()).withSelfRel();
        equipmentShopDtos.add(selfLink);
        return equipmentShopDtos;
    }
}
