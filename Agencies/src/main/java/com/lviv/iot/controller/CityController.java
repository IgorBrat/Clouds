package com.lviv.iot.controller;

import com.lviv.iot.domain.City;
import com.lviv.iot.dto.CityDto;
import com.lviv.iot.dto.assembler.CityDtoAssembler;
import com.lviv.iot.service.CityService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "cities")
public class CityController {
    @Autowired
    private CityService cityService;
    @Autowired
    private CityDtoAssembler cityDtoAssembler;

    @GetMapping(value = "")
    public ResponseEntity<CollectionModel<CityDto>> getAllCities() {
        List<City> cities = cityService.findAll();
        CollectionModel<CityDto> cityDtos = cityDtoAssembler.toCollectionModel(cities);
        return new ResponseEntity<>(cityDtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{cityId}")
    public ResponseEntity<CityDto> getCity(@PathVariable Integer cityId) {
        City city = cityService.findById(cityId);
        CityDto cityDto = cityDtoAssembler.toModel(city);
        return new ResponseEntity<>(cityDto, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<CityDto> addCity(@RequestBody City city) {
        City newCity = cityService.create(city);
        CityDto cityDto = cityDtoAssembler.toModel(newCity);
        return new ResponseEntity<>(cityDto, HttpStatus.CREATED);
    }

    @PostMapping(value = "/procedure_inserts")
    public ResponseEntity<?> insertTenCities(@RequestBody JSONObject jsonObject) {
        cityService.insertTenCities(jsonObject.getAsString("city_name"), jsonObject.getAsString("region_name"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{cityId}")
    public ResponseEntity<?> updateCity(@RequestBody City uCity, @PathVariable Integer cityId) {
        cityService.update(cityId, uCity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Integer cityId) {
        cityService.delete(cityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
