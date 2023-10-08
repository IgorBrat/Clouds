package com.lviv.iot.service;

import com.lviv.iot.domain.City;

public interface CityService extends GeneralService<City, Integer> {
    void insertTenCities(String cityName, String regionName);
}
