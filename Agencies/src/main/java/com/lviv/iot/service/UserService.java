package com.lviv.iot.service;

import com.lviv.iot.domain.User;

public interface UserService extends GeneralService<User, Integer> {
    User addUserWithProcedure(String phone, String email);
}
