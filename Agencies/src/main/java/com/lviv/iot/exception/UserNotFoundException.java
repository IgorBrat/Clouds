package com.lviv.iot.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Integer id) {
        super("Can`t find user with id: " + id);
    }
}
