package com.lviv.iot.service.impl;

import com.lviv.iot.domain.*;
import com.lviv.iot.exception.*;
import com.lviv.iot.repository.CityRepository;
import com.lviv.iot.repository.ClientCardRepository;
import com.lviv.iot.repository.ClientRepository;
import com.lviv.iot.repository.UserRepository;
import com.lviv.iot.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClientCardRepository clientCardRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Integer id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public Client create(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client create(Client client, Integer cityId, Integer userId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        client.setCity(city);
        client.setUser(user);
        return clientRepository.save(client);
    }

    @Override
    public Client create(Client client, Integer cityId, Integer userId, Integer clientCardId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        ClientCard clientCard = clientCardRepository.findById(clientCardId)
                .orElseThrow(() -> new ClientCardNotFoundException(clientCardId));
        client.setCity(city);
        client.setUser(user);
        client.setClientCard(clientCard);
        return clientRepository.save(client);
    }

    @Override
    public void update(Integer id, Client newClient) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        client.setSurname(newClient.getSurname());
        client.setName(newClient.getName());
        client.setBirthday(newClient.getBirthday());
        client.setStreetAddress(newClient.getStreetAddress());
        clientRepository.save(client);
    }

    @Override
    public void update(Integer id, Client newClient, Integer cityId, Integer userId, Integer clientCardId) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        ClientCard clientCard = clientCardRepository.findById(clientCardId)
                .orElseThrow(() -> new ClientCardNotFoundException(clientCardId));
        client.setSurname(newClient.getSurname());
        client.setName(newClient.getName());
        client.setBirthday(newClient.getBirthday());
        client.setStreetAddress(newClient.getStreetAddress());
        client.setCity(city);
        client.setUser(user);
        client.setClientCard(clientCard);
        clientRepository.save(client);
    }

    @Override
    public List<Client> findClientsByCityId(Integer cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        return city.getClients();
    }

    @Override
    public List<Order> findOrdersById(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        return client.getOrders();
    }

    @Override
    public void delete(Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        clientRepository.delete(client);
    }
}
