package com.lviv.iot.dto.assembler;

import com.lviv.iot.controller.ClientCardController;
import com.lviv.iot.domain.ClientCard;
import com.lviv.iot.dto.ClientCardDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ClientCardDtoAssembler implements RepresentationModelAssembler<ClientCard, ClientCardDto> {
    @Override
    public ClientCardDto toModel(ClientCard entity) {
        ClientCardDto clientCardDto = ClientCardDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .discountPercentage(entity.getDiscountPercentage())
                .build();
        Link selfLink = linkTo(methodOn(ClientCardController.class).getClientCard(clientCardDto.getId())).withSelfRel();
        clientCardDto.add(selfLink);
        return clientCardDto;
    }

    @Override
    public CollectionModel<ClientCardDto> toCollectionModel(Iterable<? extends ClientCard> entities) {
        CollectionModel<ClientCardDto> clientCardDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(ClientCardController.class).getAllClientCards()).withSelfRel();
        clientCardDtos.add(selfLink);
        return clientCardDtos;
    }
}
