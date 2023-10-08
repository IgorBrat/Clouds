package com.lviv.iot.service.impl;

import com.lviv.iot.domain.Client;
import com.lviv.iot.domain.ClientCard;
import com.lviv.iot.exception.ClientCardNotFoundException;
import com.lviv.iot.repository.ClientCardRepository;
import com.lviv.iot.service.ClientCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientCardServiceImpl implements ClientCardService {
    @Autowired
    private ClientCardRepository clientCardRepository;

    @Override
    public List<ClientCard> findAll() {
        return clientCardRepository.findAll();
    }

    @Override
    public ClientCard findById(Integer id) {
        return clientCardRepository.findById(id)
                .orElseThrow(() -> new ClientCardNotFoundException(id));
    }

    @Override
    public ClientCard create(ClientCard clientCard) {
        return clientCardRepository.save(clientCard);
    }

    @Override
    public void update(Integer id, ClientCard newClientCard) {
        ClientCard clientCard = clientCardRepository.findById(id)
                .orElseThrow(() -> new ClientCardNotFoundException(id));
        clientCard.setName(newClientCard.getName());
        clientCard.setDiscountPercentage(newClientCard.getDiscountPercentage());
        clientCardRepository.save(clientCard);
    }

    @Override
    public void delete(Integer id) {
        ClientCard clientCard = clientCardRepository.findById(id)
                .orElseThrow(() -> new ClientCardNotFoundException(id));
        clientCardRepository.delete(clientCard);
    }

    @Override
    public List<Client> findClientsById(Integer id) {
        ClientCard clientCard = clientCardRepository.findById(id)
                .orElseThrow(() -> new ClientCardNotFoundException(id));
        return clientCard.getClientsById();
    }
}
