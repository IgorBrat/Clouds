package com.lviv.iot.controller;

import com.lviv.iot.domain.Agency;
import com.lviv.iot.domain.Animator;
import com.lviv.iot.domain.Order;
import com.lviv.iot.dto.AgencyDto;
import com.lviv.iot.dto.AnimatorDto;
import com.lviv.iot.dto.OrderDto;
import com.lviv.iot.dto.assembler.AgencyDtoAssembler;
import com.lviv.iot.dto.assembler.AnimatorDtoAssembler;
import com.lviv.iot.dto.assembler.OrderDtoAssembler;
import com.lviv.iot.service.AnimatorService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "animators")
public class AnimatorController {
    @Autowired
    private AnimatorService animatorService;
    @Autowired
    private AnimatorDtoAssembler animatorDtoAssembler;
    @Autowired
    private AgencyDtoAssembler agencyDtoAssembler;
    @Autowired
    private OrderDtoAssembler orderDtoAssembler;

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<AnimatorDto>> getAllAnimators() {
        List<Animator> animators = animatorService.findAll();
        CollectionModel<AnimatorDto> animatorDtos = animatorDtoAssembler.toCollectionModel(animators);
        return new ResponseEntity<>(animatorDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AnimatorDto> getAnimator(@PathVariable Integer id) {
        Animator animator = animatorService.findById(id);
        AnimatorDto animatorDto = animatorDtoAssembler.toModel(animator);
        return new ResponseEntity<>(animatorDto, HttpStatus.OK);
    }

    @PostMapping(value = "/{cityId}/{userId}")
    public ResponseEntity<AnimatorDto> addAnimator(@RequestBody Animator animator, @PathVariable Integer cityId,
                                                   @PathVariable Integer userId) {
        Animator newAnimator = animatorService.create(animator, cityId, userId);
        AnimatorDto animatorDto = animatorDtoAssembler.toModel(newAnimator);
        return new ResponseEntity<>(animatorDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/{cityId}/{userId}")
    public ResponseEntity<?> updateAnimator(@RequestBody Animator uAnimator, @PathVariable Integer id,
                                            @PathVariable Integer cityId, @PathVariable Integer userId) {
        animatorService.update(id, uAnimator, cityId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteAnimator(@PathVariable Integer id) {
        animatorService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/cities/{cityId}")
    public ResponseEntity<CollectionModel<AnimatorDto>> getAnimatorsByCityId(@PathVariable Integer cityId) {
        List<Animator> animators = animatorService.findAnimatorsByCityId(cityId);
        Link selfLink = linkTo(methodOn(AnimatorController.class).getAnimatorsByCityId(cityId)).withSelfRel();
        CollectionModel<AnimatorDto> animatorDtos = animatorDtoAssembler.toCollectionModel(animators, selfLink);
        return new ResponseEntity<>(animatorDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/agencies")
    public ResponseEntity<CollectionModel<AgencyDto>> getAgenciesById(@PathVariable Integer id) {
        Set<Agency> agencies = animatorService.findAgenciesById(id);
        Link selfLink = linkTo(methodOn(AnimatorController.class).getAgenciesById(id)).withSelfRel();
        CollectionModel<AgencyDto> agencyDtos = agencyDtoAssembler.toCollectionModel(agencies, selfLink);
        return new ResponseEntity<>(agencyDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/orders")
    public ResponseEntity<CollectionModel<OrderDto>> getOrdersById(@PathVariable Integer id) {
        Set<Order> orders = animatorService.findOrdersById(id);
        Link selfLink = linkTo(methodOn(AnimatorController.class).getOrdersById(id)).withSelfRel();
        CollectionModel<OrderDto> orderDtos = orderDtoAssembler.toCollectionModel(orders, selfLink);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/avg_salary")
    public ResponseEntity<BigDecimal> getAverageSalary() {
        BigDecimal avgSalary = animatorService.getAverageSalary();
        return new ResponseEntity<>(avgSalary, HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/relation")
    public ResponseEntity<?> addAnimatorAgencyRelationship(@RequestBody JSONObject jsonObject) {
        animatorService.addAnimatorAgencyRelationship(jsonObject.getAsString("animator_surname"), jsonObject.getAsString("animator_name"),
                jsonObject.getAsString("agency_name"), jsonObject.getAsString("agency_owner"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
