package com.lviv.iot.service;

import com.lviv.iot.domain.Client;
import com.lviv.iot.domain.Order;

import java.util.List;

public interface ClientService extends GeneralService<Client, Integer> {
    Client create(Client client, Integer cityId, Integer userId);
    Client create(Client client, Integer cityId, Integer userId, Integer clientCardId);
    void update(Integer id, Client newClient, Integer cityId, Integer userId,  Integer clientCardId);
    List<Client> findClientsByCityId(Integer cityId);
    List<Order> findOrdersById(Integer id);
}
