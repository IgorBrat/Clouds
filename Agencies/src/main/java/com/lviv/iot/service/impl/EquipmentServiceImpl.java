package com.lviv.iot.service.impl;

import com.lviv.iot.domain.Equipment;
import com.lviv.iot.domain.Event;
import com.lviv.iot.exception.EquipmentNotFoundException;
import com.lviv.iot.repository.EquipmentRepository;
import com.lviv.iot.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment findById(Integer id) {
        return equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
    }

    @Override
    public Equipment create(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public void update(Integer id, Equipment newEquipment) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
        equipment.setName(newEquipment.getName());
        equipment.setShopId(newEquipment.getShopId());
        equipmentRepository.save(equipment);
    }

    @Override
    public void delete(Integer id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
        equipmentRepository.delete(equipment);
    }

    @Override
    public Set<Event> findEventsById(Integer id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException(id));
        return equipment.getEvents();
    }
}
