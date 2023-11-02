package com.lviv.iot.service;

import com.lviv.iot.domain.Agency;
import com.lviv.iot.domain.Animator;
import com.lviv.iot.domain.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface AnimatorService extends GeneralService<Animator, Integer> {
    Animator create(Animator animator, Integer cityId, Integer userId);
    void update(Integer id, Animator animator, Integer cityId, Integer userId);
    List<Animator> findAnimatorsByCityId(Integer cityId);
    Set<Agency> findAgenciesById(Integer id);
    Set<Order> findOrdersById(Integer id);
    BigDecimal getAverageSalary();
    void addAnimatorAgencyRelationship(String animatorSurname, String animatorName,
                                       String agency_name, String owner);
}
