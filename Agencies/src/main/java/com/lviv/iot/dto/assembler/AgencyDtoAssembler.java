package com.lviv.iot.dto.assembler;

import com.lviv.iot.controller.AgencyController;
import com.lviv.iot.domain.Agency;
import com.lviv.iot.dto.AgencyDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AgencyDtoAssembler implements RepresentationModelAssembler<Agency, AgencyDto> {
    @Override
    public AgencyDto toModel(Agency entity) {
        AgencyDto agencyDto = AgencyDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .owner(entity.getOwner())
                .hqAddress(entity.getHqAddress())
                .city_name(entity.getCity().getName())
                .region_name(entity.getCity().getRegionName())
                .phone(entity.getUser().getPhone())
                .email(entity.getUser().getEmail())
                .build();
        Link selfLink = linkTo(methodOn(AgencyController.class).getAgency(agencyDto.getId())).withSelfRel();
        agencyDto.add(selfLink);
        return agencyDto;
    }

    @Override
    public CollectionModel<AgencyDto> toCollectionModel(Iterable<? extends Agency> entities) {
        CollectionModel<AgencyDto> agencyDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(AgencyController.class).getAllAgencies()).withSelfRel();
        agencyDtos.add(selfLink);
        return agencyDtos;
    }

    public CollectionModel<AgencyDto> toCollectionModel(Iterable<? extends Agency> entities, Link link) {
        CollectionModel<AgencyDto> agencyDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        agencyDtos.add(link);
        return agencyDtos;
    }
}
