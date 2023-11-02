package com.lviv.iot.service;

import com.lviv.iot.domain.Equipment;
import com.lviv.iot.domain.Event;
import com.lviv.iot.domain.EventEquipment;

import java.util.List;
import java.util.Set;

public interface EventService extends GeneralService<Event, Integer> {
    Set<Equipment> findEquipmentsById(Integer id);
    List<EventEquipment> findEquipmentsAndQuantityById(Integer id);
}
