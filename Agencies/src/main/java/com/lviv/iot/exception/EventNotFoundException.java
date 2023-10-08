package com.lviv.iot.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(Integer id) {
        super("Can`t find event with id: " + id);
    }
}
