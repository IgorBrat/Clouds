package com.lviv.iot.service;

import com.lviv.iot.domain.Animator;
import com.lviv.iot.domain.Order;

import java.util.List;
import java.util.Set;

public interface OrderService extends GeneralService<Order, Integer> {
    Order create(Order order, Integer cityId, Integer clientId, Integer eventId);
    void update(Integer id, Order newOrder, Integer cityId, Integer clientId, Integer eventId);
    List<Order> findOrdersByCityId(Integer cityId);
    Set<Animator> findAnimatorsById(Integer id);
}
