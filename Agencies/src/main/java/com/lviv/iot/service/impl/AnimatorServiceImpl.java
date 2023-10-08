package com.lviv.iot.service.impl;

import com.lviv.iot.domain.*;
import com.lviv.iot.exception.AnimatorNotFoundException;
import com.lviv.iot.exception.CityNotFoundException;
import com.lviv.iot.exception.UserNotFoundException;
import com.lviv.iot.repository.AnimatorRepository;
import com.lviv.iot.repository.CityRepository;
import com.lviv.iot.repository.UserRepository;
import com.lviv.iot.service.AnimatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class AnimatorServiceImpl implements AnimatorService {
    @Autowired
    private AnimatorRepository animatorRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Animator> findAll() {
        return animatorRepository.findAll();
    }

    @Override
    public Animator findById(Integer id) {
        return animatorRepository.findById(id)
                .orElseThrow(() -> new AnimatorNotFoundException(id));
    }

    @Override
    public Animator create(Animator animator) {
        return animatorRepository.save(animator);
    }

    @Override
    public Animator create(Animator animator, Integer cityId, Integer userId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        animator.setCity(city);
        animator.setUser(user);
        return animatorRepository.save(animator);
    }

    @Override
    public void update(Integer id, Animator newAnimator) {
        Animator animator = animatorRepository.findById(id)
                .orElseThrow(() -> new AnimatorNotFoundException(id));
        animator.setSurname(newAnimator.getSurname());
        animator.setName(newAnimator.getName());
        animator.setSalaryPerHour(newAnimator.getSalaryPerHour());
        animatorRepository.save(animator);
    }

    @Override
    public void update(Integer id, Animator newAnimator, Integer cityId, Integer userId) {
        Animator animator = animatorRepository.findById(id)
                .orElseThrow(() -> new AnimatorNotFoundException(id));
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        animator.setCity(city);
        animator.setUser(user);
        animator.setSurname(newAnimator.getSurname());
        animator.setName(newAnimator.getName());
        animator.setSalaryPerHour(newAnimator.getSalaryPerHour());
        animatorRepository.save(animator);
    }

    @Override
    public List<Animator> findAnimatorsByCityId(Integer cityId) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(cityId));
        return city.getAnimators();
    }

    @Override
    public Set<Agency> findAgenciesById(Integer id) {
        Animator animator =  animatorRepository.findById(id)
                .orElseThrow(() -> new AnimatorNotFoundException(id));
        return animator.getAgencies();
    }

    @Override
    public Set<Order> findOrdersById(Integer id) {
        Animator animator =  animatorRepository.findById(id)
                .orElseThrow(() -> new AnimatorNotFoundException(id));
        return animator.getOrders();
    }

    @Override
    public BigDecimal getAverageSalary() {
        return animatorRepository.getAverageSalary();
    }

    @Override
    public void addAnimatorAgencyRelationship(String animatorSurname, String animatorName, String agency_name, String owner) {
        animatorRepository.addAnimatorAgencyRelationship(animatorSurname, animatorName, agency_name, owner);
    }

    @Override
    public void delete(Integer id) {
        Animator animator = animatorRepository.findById(id)
                .orElseThrow(() -> new AnimatorNotFoundException(id));
        animatorRepository.delete(animator);
    }
}
