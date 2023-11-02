package com.lviv.iot.service.impl;

import com.lviv.iot.domain.EquipmentShop;
import com.lviv.iot.exception.EquipmentShopNotFoundException;
import com.lviv.iot.repository.EquipmentShopRepository;
import com.lviv.iot.service.EquipmentShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentShopServiceImpl implements EquipmentShopService {
    @Autowired
    private EquipmentShopRepository equipmentShopRepository;
    @Override
    public List<EquipmentShop> findAll() {
        return equipmentShopRepository.findAll();
    }

    @Override
    public EquipmentShop findById(Integer id) {
        return equipmentShopRepository.findById(id)
                .orElseThrow(() -> new EquipmentShopNotFoundException(id));
    }

    @Override
    public EquipmentShop create(EquipmentShop equipmentShop) {
        return equipmentShopRepository.save(equipmentShop);
    }

    @Override
    public void update(Integer id, EquipmentShop newEquipmentShop) {
        EquipmentShop equipmentShop = equipmentShopRepository.findById(id)
                .orElseThrow(() -> new EquipmentShopNotFoundException(id));
        equipmentShop.setName(newEquipmentShop.getName());
        equipmentShopRepository.save(equipmentShop);
    }

    @Override
    public void delete(Integer id) {
        EquipmentShop equipmentShop = equipmentShopRepository.findById(id)
                .orElseThrow(() -> new EquipmentShopNotFoundException(id));
        equipmentShopRepository.delete(equipmentShop);
    }
}
