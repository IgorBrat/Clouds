package com.lviv.iot.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Integer id) {
        super("Can`t find order with id: " + id);
    }
}
