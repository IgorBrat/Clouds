package com.lviv.iot.controller;

import com.lviv.iot.domain.Agency;
import com.lviv.iot.domain.Animator;
import com.lviv.iot.dto.AgencyDto;
import com.lviv.iot.dto.AnimatorDto;
import com.lviv.iot.dto.assembler.AgencyDtoAssembler;
import com.lviv.iot.dto.assembler.AnimatorDtoAssembler;
import com.lviv.iot.service.AgencyService;
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
@RequestMapping(value = "agencies")
public class AgencyController {
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private AgencyDtoAssembler agencyDtoAssembler;
    @Autowired
    private AnimatorDtoAssembler animatorDtoAssembler;

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<AgencyDto>> getAllAgencies() {
        List<Agency> agencies = agencyService.findAll();
        CollectionModel<AgencyDto> agencyDtos = agencyDtoAssembler.toCollectionModel(agencies);
        return new ResponseEntity<>(agencyDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AgencyDto> getAgency(@PathVariable Integer id) {
        Agency agency = agencyService.findById(id);
        AgencyDto agencyDto = agencyDtoAssembler.toModel(agency);
        return new ResponseEntity<>(agencyDto, HttpStatus.OK);
    }

    @PostMapping(value = "/{cityId}/{userId}")
    public ResponseEntity<AgencyDto> addAgency(@RequestBody Agency agency, @PathVariable Integer cityId,
                                                   @PathVariable Integer userId) {
        Agency newAgency = agencyService.create(agency, cityId, userId);
        AgencyDto agencyDto = agencyDtoAssembler.toModel(newAgency);
        return new ResponseEntity<>(agencyDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/{cityId}/{userId}")
    public ResponseEntity<?> updateAgency(@RequestBody Agency uAgency, @PathVariable Integer id,
                                            @PathVariable Integer cityId, @PathVariable Integer userId) {
        agencyService.update(id, uAgency, cityId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAgency(@PathVariable Integer id) {
        agencyService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/cities/{cityId}")
    public ResponseEntity<CollectionModel<AgencyDto>> getAgenciesByCityId(@PathVariable Integer cityId) {
        List<Agency> agencies = agencyService.findAgenciesByCityId(cityId);
        Link selfLink = linkTo(methodOn(AgencyController.class).getAgenciesByCityId(cityId)).withSelfRel();
        CollectionModel<AgencyDto> agencyDtos = agencyDtoAssembler.toCollectionModel(agencies, selfLink);
        return new ResponseEntity<>(agencyDtos, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/animators")
    public ResponseEntity<CollectionModel<AnimatorDto>> getAnimatorsById(@PathVariable Integer id) {
        Set<Animator> animators = agencyService.findAnimatorsById(id);
        Link selfLink = linkTo(methodOn(AgencyController.class).getAnimatorsById(id)).withSelfRel();
        CollectionModel<AnimatorDto> animatorDtos = animatorDtoAssembler.toCollectionModel(animators, selfLink);
        return new ResponseEntity<>(animatorDtos, HttpStatus.OK);
    }

    @PostMapping(value = "/cursor")
    public ResponseEntity<?> createTablesWithCursor() {
        agencyService.createTablesWithCursor();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
