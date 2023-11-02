package com.lviv.iot.exception;

public class CityNotFoundException extends RuntimeException{
    public CityNotFoundException(Integer cityId) {
        super("Can`t find city with id: " + cityId);
    }
}
