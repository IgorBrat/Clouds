package com.lviv.iot.controller;

import com.lviv.iot.domain.Client;
import com.lviv.iot.domain.ClientCard;
import com.lviv.iot.dto.ClientCardDto;
import com.lviv.iot.dto.ClientDto;
import com.lviv.iot.dto.assembler.ClientCardDtoAssembler;
import com.lviv.iot.dto.assembler.ClientDtoAssembler;
import com.lviv.iot.service.ClientCardService;
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
@RequestMapping(value = "clientCards")
public class ClientCardController {
    @Autowired
    private ClientCardService clientCardService;
    @Autowired
    private ClientCardDtoAssembler clientCardDtoAssembler;
    @Autowired
    private ClientDtoAssembler clientDtoAssembler;

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<ClientCardDto>> getAllClientCards() {
        List<ClientCard> clientCards = clientCardService.findAll();
        CollectionModel<ClientCardDto> clientCardDtos = clientCardDtoAssembler.toCollectionModel(clientCards);
        return new ResponseEntity<>(clientCardDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientCardDto> getClientCard(@PathVariable Integer id) {
        ClientCard clientCard = clientCardService.findById(id);
        ClientCardDto clientCardDto = clientCardDtoAssembler.toModel(clientCard);
        return new ResponseEntity<>(clientCardDto, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<ClientCardDto> addClientCard(@RequestBody ClientCard clientCard) {
        ClientCard newClientCard = clientCardService.create(clientCard);
        ClientCardDto clientCardDto = clientCardDtoAssembler.toModel(newClientCard);
        return new ResponseEntity<>(clientCardDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateClientCard(@RequestBody ClientCard uClientCard, @PathVariable Integer id) {
        clientCardService.update(id, uClientCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClientCard(@PathVariable Integer id) {
        clientCardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/clients")
    public ResponseEntity<CollectionModel<ClientDto>> getClientsById(@PathVariable Integer id) {
        List<Client> clients = clientCardService.findClientsById(id);
        Link selfLink = linkTo(methodOn(ClientCardController.class).getClientsById(id)).withSelfRel();
        CollectionModel<ClientDto> clientDtos = clientDtoAssembler.toCollectionModel(clients, selfLink);
        return new ResponseEntity<>(clientDtos, HttpStatus.OK);
    }
}
