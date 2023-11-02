package com.lviv.iot.exception;

public class AgencyNotFoundException extends RuntimeException{
    public AgencyNotFoundException(Integer id) {
        super("Can`t find agency with id: " + id);
    }
}
