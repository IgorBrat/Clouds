package com.lviv.iot.controller;

import com.lviv.iot.domain.EquipmentShop;
import com.lviv.iot.dto.EquipmentShopDto;
import com.lviv.iot.dto.assembler.EquipmentShopDtoAssembler;
import com.lviv.iot.service.EquipmentShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "equipmentShops")
public class EquipmentShopController {
    @Autowired
    private EquipmentShopService equipmentShopService;
    @Autowired
    private EquipmentShopDtoAssembler equipmentShopDtoAssembler;

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<EquipmentShopDto>> getAllEquipmentShops() {
        List<EquipmentShop> equipmentShops = equipmentShopService.findAll();
        CollectionModel<EquipmentShopDto> equipmentShopDtos = equipmentShopDtoAssembler.toCollectionModel(equipmentShops);
        return new ResponseEntity<>(equipmentShopDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<EquipmentShopDto> getEquipmentShop(@PathVariable Integer id) {
        EquipmentShop equipmentShop = equipmentShopService.findById(id);
        EquipmentShopDto equipmentShopDto = equipmentShopDtoAssembler.toModel(equipmentShop);
        return new ResponseEntity<>(equipmentShopDto, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<EquipmentShopDto> addEquipmentShop(@RequestBody EquipmentShop equipmentShop) {
        EquipmentShop newEquipmentShop = equipmentShopService.create(equipmentShop);
        EquipmentShopDto equipmentShopDto = equipmentShopDtoAssembler.toModel(newEquipmentShop);
        return new ResponseEntity<>(equipmentShopDto, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateEquipmentShop(@RequestBody EquipmentShop uEquipmentShop, @PathVariable Integer id) {
        equipmentShopService.update(id, uEquipmentShop);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteEquipmentShop(@PathVariable Integer id) {
        equipmentShopService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
