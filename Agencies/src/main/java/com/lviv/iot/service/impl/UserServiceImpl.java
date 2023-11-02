package com.lviv.iot.service.impl;

import com.lviv.iot.domain.User;
import com.lviv.iot.exception.UserNotFoundException;
import com.lviv.iot.repository.UserRepository;
import com.lviv.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public void update(Integer userId, User newUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setEmail(newUser.getEmail());
        user.setPhone(newUser.getPhone());
        userRepository.save(user);
    }

    @Override
    public void delete(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.delete(user);
    }

    @Override
    public User addUserWithProcedure(String phone, String email) {
        return userRepository.addUserWithProcedure(phone, email);
    }
}
