package com.lviv.iot.service.impl;

import com.lviv.iot.domain.*;
import com.lviv.iot.exception.AgencyNotFoundException;
import com.lviv.iot.exception.CityNotFoundException;
import com.lviv.iot.exception.UserNotFoundException;
import com.lviv.iot.repository.AgencyRepository;
import com.lviv.iot.repository.CityRepository;
import com.lviv.iot.repository.UserRepository;
import com.lviv.iot.service.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AgencyServiceImpl implements AgencyService {
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Agency> findAll() {
        return agencyRepository.findAll();
    }

    @Override
    public Agency findById(Integer id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new AgencyNotFoundException(id));
    }

    @Override
    public Agency create(Agency agency) {
        return agencyRepository.save(agency);
    }

    @Override
    public Agency create(Agency agency, Integer cityId, Integer userId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        agency.setCity(city);
        agency.setUser(user);
        return agencyRepository.save(agency);
    }

    @Override
    public void update(Integer id, Agency newAgency) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new AgencyNotFoundException(id));
        agency.setName(newAgency.getName());
        agency.setOwner(newAgency.getOwner());
        agency.setHqAddress(newAgency.getHqAddress());
        agencyRepository.save(agency);
    }

    @Override
    public void update(Integer id, Agency newAgency, Integer cityId, Integer userId) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new AgencyNotFoundException(id));
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        agency.setName(newAgency.getName());
        agency.setOwner(newAgency.getOwner());
        agency.setHqAddress(newAgency.getHqAddress());
        agency.setCity(city);
        agency.setUser(user);
        agencyRepository.save(agency);
    }

    @Override
    public List<Agency> findAgenciesByCityId(Integer cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        return city.getAgencies();
    }

    @Override
    public Set<Animator> findAnimatorsById(Integer id) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new AgencyNotFoundException(id));
        return agency.getAnimators();
    }

    @Override
    public void createTablesWithCursor() {
        agencyRepository.createTablesWithCursor();
    }

    @Override
    public void delete(Integer id) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new AgencyNotFoundException(id));
        agencyRepository.delete(agency);
    }
}
