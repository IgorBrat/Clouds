package com.lviv.iot.dto.assembler;

import com.lviv.iot.controller.AnimatorController;
import com.lviv.iot.domain.Animator;
import com.lviv.iot.dto.AnimatorDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnimatorDtoAssembler implements RepresentationModelAssembler<Animator, AnimatorDto> {

    @Override
    public AnimatorDto toModel(Animator entity) {
        AnimatorDto animatorDto = AnimatorDto.builder()
                .id(entity.getId())
                .surname(entity.getSurname())
                .name(entity.getName())
                .salaryPerHour(entity.getSalaryPerHour())
                .cityName(entity.getCity().getName())
                .regionName(entity.getCity().getRegionName())
                .phone(entity.getUser().getPhone())
                .email(entity.getUser().getEmail())
                .build();
        Link selfLink = linkTo(methodOn(AnimatorController.class).getAnimator(animatorDto.getId())).withSelfRel();
        animatorDto.add(selfLink);
        return animatorDto;
    }

    @Override
    public CollectionModel<AnimatorDto> toCollectionModel(Iterable<? extends Animator> entities) {
        CollectionModel<AnimatorDto> animatorDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(AnimatorController.class).getAllAnimators()).withSelfRel();
        animatorDtos.add(selfLink);
        return animatorDtos;
    }

    public CollectionModel<AnimatorDto> toCollectionModel(Iterable<? extends Animator> entities, Link link) {
        CollectionModel<AnimatorDto> animatorDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        animatorDtos.add(link);
        return animatorDtos;
    }
}
