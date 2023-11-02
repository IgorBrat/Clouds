package com.lviv.iot.service.impl;

import com.lviv.iot.domain.City;
import com.lviv.iot.exception.CityNotFoundException;
import com.lviv.iot.repository.CityRepository;
import com.lviv.iot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public City findById(Integer cityId) {
        return cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }

    @Override
    public City create(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void update(Integer cityId, City newCity) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        city.setName(newCity.getName());
        city.setRegionName(newCity.getRegionName());
        cityRepository.save(city);
    }

    @Override
    public void delete(Integer cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        cityRepository.delete(city);
    }

    @Override
    public void insertTenCities(String cityName, String regionName) {
        cityRepository.insertTenCities(cityName, regionName);
    }
}
