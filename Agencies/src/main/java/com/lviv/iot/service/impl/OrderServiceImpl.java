package com.lviv.iot.service.impl;

import com.lviv.iot.domain.*;
import com.lviv.iot.exception.*;
import com.lviv.iot.repository.CityRepository;
import com.lviv.iot.repository.ClientRepository;
import com.lviv.iot.repository.EventRepository;
import com.lviv.iot.repository.OrderRepository;
import com.lviv.iot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Order> findAll() {
        for (Order order : orderRepository.findAll()) {
            System.out.println(order.toString());
        }
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order create(Order order, Integer cityId, Integer clientId, Integer eventId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        order.setCity(city);
        order.setClient(client);
        order.setEvent(event);
        return orderRepository.save(order);
    }

    @Override
    public void update(Integer id, Order newOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        order.setDatetime(newOrder.getDatetime());
        order.setDuration(newOrder.getDuration());
        order.setStreetAddress(newOrder.getStreetAddress());
        order.setTotalPrice(newOrder.getTotalPrice());
        orderRepository.save(order);
    }

    @Override
    public void update(Integer id, Order newOrder, Integer cityId, Integer clientId, Integer eventId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException(clientId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        order.setDatetime(newOrder.getDatetime());
        order.setDuration(newOrder.getDuration());
        order.setStreetAddress(newOrder.getStreetAddress());
        order.setTotalPrice(newOrder.getTotalPrice());
        order.setCity(city);
        order.setClient(client);
        order.setEvent(event);
        orderRepository.save(order);
    }

    @Override
    public List<Order> findOrdersByCityId(Integer cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        return city.getOrders();
    }

    @Override
    public Set<Animator> findAnimatorsById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return order.getAnimators();
    }

    @Override
    public void delete(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        orderRepository.delete(order);
    }
}
