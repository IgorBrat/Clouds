package com.lviv.iot.controller;

import com.lviv.iot.domain.Client;
import com.lviv.iot.domain.Order;
import com.lviv.iot.dto.ClientDto;
import com.lviv.iot.dto.OrderDto;
import com.lviv.iot.dto.assembler.ClientDtoAssembler;
import com.lviv.iot.dto.assembler.OrderDtoAssembler;
import com.lviv.iot.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "clients")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientDtoAssembler clientDtoAssembler;
    @Autowired
    private OrderDtoAssembler orderDtoAssembler;

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<ClientDto>> getAllClients() {
        List<Client> clients = clientService.findAll();
        CollectionModel<ClientDto> clientDtos = clientDtoAssembler.toCollectionModel(clients);
        return new ResponseEntity<>(clientDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Integer id) {
        Client client = clientService.findById(id);
        ClientDto clientDto = clientDtoAssembler.toModel(client);
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @PostMapping(value = "/{cityId}/{userId}")
    public ResponseEntity<ClientDto> addClient(@RequestBody Client client, @PathVariable Integer cityId,
                                               @PathVariable Integer userId) {
        Client newClient = clientService.create(client, cityId, userId);
        ClientDto clientDto = clientDtoAssembler.toModel(newClient);
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{cityId}/{userId}/{clientCardId}")
    public ResponseEntity<ClientDto> addClientWithClientCard(@RequestBody Client client, @PathVariable Integer cityId,
                                                   @PathVariable Integer userId, @PathVariable Integer clientCardId) {
        Client newClient = clientService.create(client, cityId, userId, clientCardId);
        ClientDto clientDto = clientDtoAssembler.toModel(newClient);
        return new ResponseEntity<>(clientDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/{cityId}/{userId}/{clientCardId}")
    public ResponseEntity<?> updateClient(@RequestBody Client uClient, @PathVariable Integer id,
                                            @PathVariable Integer cityId, @PathVariable Integer userId,
                                            @PathVariable Integer clientCardId) {
        clientService.update(id, uClient, cityId, userId, clientCardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Integer id) {
        clientService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/cities/{cityId}")
    public ResponseEntity<CollectionModel<ClientDto>> getClientsByCityId(@PathVariable Integer cityId) {
        List<Client> clients = clientService.findClientsByCityId(cityId);
        Link selfLink = linkTo(methodOn(ClientController.class).getClientsByCityId(cityId)).withSelfRel();
        CollectionModel<ClientDto> clientDtos = clientDtoAssembler.toCollectionModel(clients, selfLink);
        return new ResponseEntity<>(clientDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/orders")
    public ResponseEntity<CollectionModel<OrderDto>> getOrdersById(@PathVariable Integer id) {
        List<Order> orders = clientService.findOrdersById(id);
        Link selfLink = linkTo(methodOn(AnimatorController.class).getOrdersById(id)).withSelfRel();
        CollectionModel<OrderDto> orderDtos = orderDtoAssembler.toCollectionModel(orders, selfLink);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }
}
