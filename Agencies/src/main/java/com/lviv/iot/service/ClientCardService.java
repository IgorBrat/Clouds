package com.lviv.iot.service;

import com.lviv.iot.domain.Client;
import com.lviv.iot.domain.ClientCard;

import java.util.List;

public interface ClientCardService extends GeneralService<ClientCard, Integer> {
    List<Client> findClientsById(Integer id);
}
