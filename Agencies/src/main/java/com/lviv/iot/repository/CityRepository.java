package com.lviv.iot.repository;

import com.lviv.iot.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    @Procedure("CityTestInserts")
    void insertTenCities(String cityName, String regionName);
}
