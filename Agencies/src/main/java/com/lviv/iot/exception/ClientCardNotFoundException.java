package com.lviv.iot.exception;

public class ClientCardNotFoundException extends RuntimeException{
    public ClientCardNotFoundException(Integer id) {
        super("Can`t find client card with id: " + id);
    }
}
