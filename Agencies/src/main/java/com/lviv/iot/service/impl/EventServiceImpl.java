package com.lviv.iot.service.impl;

import com.lviv.iot.domain.Equipment;
import com.lviv.iot.domain.Event;
import com.lviv.iot.domain.EventEquipment;
import com.lviv.iot.exception.EventNotFoundException;
import com.lviv.iot.repository.EventRepository;
import com.lviv.iot.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Override
    public Event findById(Integer id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }

    @Override
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void update(Integer id, Event newEvent) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        event.setName(newEvent.getName());
        eventRepository.save(event);
    }

    @Override
    public void delete(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        eventRepository.delete(event);
    }

    @Override
    public Set<Equipment> findEquipmentsById(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return event.getEquipments();
    }

    @Override
    public List<EventEquipment> findEquipmentsAndQuantityById(Integer id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return event.getEquipmentsRelation();
    }
}
