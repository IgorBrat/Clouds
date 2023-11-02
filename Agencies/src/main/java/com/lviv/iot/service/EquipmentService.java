package com.lviv.iot.service;

import com.lviv.iot.domain.Equipment;
import com.lviv.iot.domain.Event;

import java.util.Set;

public interface EquipmentService extends GeneralService<Equipment, Integer> {
    Set<Event> findEventsById(Integer id);
}
