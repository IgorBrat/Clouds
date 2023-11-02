package com.lviv.iot.exception;

public class EquipmentNotFoundException extends RuntimeException{
    public EquipmentNotFoundException(Integer id) {
        super("Can`t find equipment with id: " + id);
    }
}
