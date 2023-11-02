package com.lviv.iot.exception;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Integer id) {
        super("Can`t find client with id: " + id);
    }
}
