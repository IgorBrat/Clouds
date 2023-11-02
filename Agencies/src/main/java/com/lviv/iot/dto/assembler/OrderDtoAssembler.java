package com.lviv.iot.dto.assembler;

import com.lviv.iot.controller.OrderController;
import com.lviv.iot.domain.Order;
import com.lviv.iot.dto.OrderDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderDtoAssembler implements RepresentationModelAssembler<Order, OrderDto> {
    @Override
    public OrderDto toModel(Order entity) {
        OrderDto orderDto = OrderDto.builder()
                .id(entity.getId())
                .datetime(entity.getDatetime())
                .duration(entity.getDuration())
                .streetAddress(entity.getStreetAddress())
                .totalPrice(entity.getTotalPrice())
                .eventName(entity.getEvent().getName())
                .cityName(entity.getCity().getName())
                .regionName(entity.getCity().getRegionName())
                .clientSurname(entity.getClient().getSurname())
                .clientEmail(entity.getClient().getUser().getEmail())
                .build();
        Link selfLink = linkTo(methodOn(OrderController.class).getOrder(orderDto.getId())).withSelfRel();
        orderDto.add(selfLink);
        return orderDto;
    }

    @Override
    public CollectionModel<OrderDto> toCollectionModel(Iterable<? extends Order> entities) {
        CollectionModel<OrderDto> orderDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel();
        orderDtos.add(selfLink);
        return orderDtos;
    }

    public CollectionModel<OrderDto> toCollectionModel(Iterable<? extends Order> entities, Link link) {
        CollectionModel<OrderDto> orderDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        orderDtos.add(link);
        return orderDtos;
    }
}
