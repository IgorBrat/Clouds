package com.lviv.iot.service;

import com.lviv.iot.domain.Agency;
import com.lviv.iot.domain.Animator;

import java.util.List;
import java.util.Set;

public interface AgencyService extends GeneralService<Agency, Integer> {
    Agency create(Agency agency, Integer cityId, Integer userId);
    void update(Integer id, Agency newAgency, Integer cityId, Integer userId);
    List<Agency> findAgenciesByCityId(Integer cityId);
    Set<Animator> findAnimatorsById(Integer id);
    void createTablesWithCursor();
}
