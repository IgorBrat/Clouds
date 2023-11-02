package com.lviv.iot.controller;

import com.lviv.iot.domain.Equipment;
import com.lviv.iot.domain.Event;
import com.lviv.iot.dto.EquipmentDto;
import com.lviv.iot.dto.EventDto;
import com.lviv.iot.dto.assembler.EquipmentDtoAssembler;
import com.lviv.iot.dto.assembler.EventDtoAssembler;
import com.lviv.iot.service.EquipmentService;
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
@RequestMapping(value = "equipments")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentDtoAssembler equipmentDtoAssembler;
    @Autowired
    private EventDtoAssembler eventDtoAssembler;

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<EquipmentDto>> getAllEquipments() {
        List<Equipment> equipments = equipmentService.findAll();
        CollectionModel<EquipmentDto> equipmentDtos = equipmentDtoAssembler.toCollectionModel(equipments);
        return new ResponseEntity<>(equipmentDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipmentDto> getEquipment(@PathVariable Integer id) {
        Equipment equipment = equipmentService.findById(id);
        EquipmentDto equipmentDto = equipmentDtoAssembler.toModel(equipment);
        return new ResponseEntity<>(equipmentDto, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<EquipmentDto> addEquipment(@RequestBody Equipment equipment) {
        Equipment newEquipment = equipmentService.create(equipment);
        EquipmentDto equipmentDto = equipmentDtoAssembler.toModel(newEquipment);
        return new ResponseEntity<>(equipmentDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateEquipment(@RequestBody Equipment uEquipment, @PathVariable Integer id) {
        equipmentService.update(id, uEquipment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable Integer id) {
        equipmentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/events")
    public ResponseEntity<CollectionModel<EventDto>> getEventsById(@PathVariable Integer id) {
        Set<Event> events = equipmentService.findEventsById(id);
        Link selfLink = linkTo(methodOn(EquipmentController.class).getEventsById(id)).withSelfRel();
        CollectionModel<EventDto> eventDtos = eventDtoAssembler.toCollectionModel(events, selfLink);
        return new ResponseEntity<>(eventDtos, HttpStatus.OK);
    }
}
