package com.lviv.iot.dto.assembler;

import com.lviv.iot.controller.CityController;
import com.lviv.iot.domain.City;
import com.lviv.iot.dto.CityDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CityDtoAssembler implements RepresentationModelAssembler<City, CityDto> {
    @Override
    public CityDto toModel(City entity) {
        CityDto cityDto = CityDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .regionName(entity.getRegionName())
                .build();
        Link selfLink = linkTo(methodOn(CityController.class).getCity(cityDto.getId())).withSelfRel();
        cityDto.add(selfLink);
        return cityDto;
    }

    @Override
    public CollectionModel<CityDto> toCollectionModel(Iterable<? extends City> entities) {
        CollectionModel<CityDto> cityDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(CityController.class).getAllCities()).withSelfRel();
        cityDtos.add(selfLink);
        return cityDtos;
    }
}
