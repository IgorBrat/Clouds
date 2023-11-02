package com.lviv.iot.dto.assembler;

import com.lviv.iot.controller.EventController;
import com.lviv.iot.domain.Event;
import com.lviv.iot.dto.EventDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventDtoAssembler implements RepresentationModelAssembler<Event, EventDto> {

    @Override
    public EventDto toModel(Event entity) {
        EventDto eventDto = EventDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
        Link selfLink = linkTo(methodOn(EventController.class).getEvent(eventDto.getId())).withSelfRel();
        eventDto.add(selfLink);
        return eventDto;
    }

    @Override
    public CollectionModel<EventDto> toCollectionModel(Iterable<? extends Event> entities) {
        CollectionModel<EventDto> eventDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        Link selfLink = linkTo(methodOn(EventController.class).getAllEvents()).withSelfRel();
        eventDtos.add(selfLink);
        return eventDtos;
    }

    public CollectionModel<EventDto> toCollectionModel(Iterable<? extends Event> entities, Link link) {
        CollectionModel<EventDto> eventDtos = RepresentationModelAssembler.super.toCollectionModel(entities);
        eventDtos.add(link);
        return eventDtos;
    }
}
