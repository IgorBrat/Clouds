package com.lviv.iot.controller;

import com.lviv.iot.domain.Animator;
import com.lviv.iot.domain.Order;
import com.lviv.iot.dto.AnimatorDto;
import com.lviv.iot.dto.OrderDto;
import com.lviv.iot.dto.assembler.AnimatorDtoAssembler;
import com.lviv.iot.dto.assembler.OrderDtoAssembler;
import com.lviv.iot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDtoAssembler orderDtoAssembler;
    @Autowired
    private AnimatorDtoAssembler animatorDtoAssembler;

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<OrderDto>> getAllOrders() {
        List<Order> orders = orderService.findAll();
        CollectionModel<OrderDto> orderDtos = orderDtoAssembler.toCollectionModel(orders);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Integer id) {
        Order order = orderService.findById(id);
        OrderDto orderDto = orderDtoAssembler.toModel(order);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping(value = "/{cityId}/{clientId}/{eventId}")
    public ResponseEntity<OrderDto> addOrder(@RequestBody Order order, @PathVariable Integer cityId,
                                                   @PathVariable Integer clientId, @PathVariable Integer eventId) {
        Order newOrder = orderService.create(order, cityId, clientId, eventId);
        OrderDto orderDto = orderDtoAssembler.toModel(newOrder);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/{cityId}/{clientId}/{eventId}")
    public ResponseEntity<?> updateOrder(@RequestBody Order uOrder, @PathVariable Integer id,
                                            @PathVariable Integer cityId, @PathVariable Integer clientId,
                                         @PathVariable Integer eventId) {
        orderService.update(id, uOrder, cityId, clientId, eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/cities/{cityId}")
    public ResponseEntity<CollectionModel<OrderDto>> getOrdersByCityId(@PathVariable Integer cityId) {
        List<Order> orders = orderService.findOrdersByCityId(cityId);
        Link selfLink = linkTo(methodOn(OrderController.class).getOrdersByCityId(cityId)).withSelfRel();
        CollectionModel<OrderDto> orderDtos = orderDtoAssembler.toCollectionModel(orders, selfLink);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/animators")
    public ResponseEntity<CollectionModel<AnimatorDto>> getAnimatorsById(@PathVariable Integer id) {
        Set<Animator> animators = orderService.findAnimatorsById(id);
        Link selfLink = linkTo(methodOn(OrderController.class).getAnimatorsById(id)).withSelfRel();
        CollectionModel<AnimatorDto> animatorDtos = animatorDtoAssembler.toCollectionModel(animators, selfLink);
        return new ResponseEntity<>(animatorDtos, HttpStatus.OK);
    }
}
