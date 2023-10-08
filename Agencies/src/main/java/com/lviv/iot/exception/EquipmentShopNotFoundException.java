package com.lviv.iot.exception;

public class EquipmentShopNotFoundException extends RuntimeException{
    public EquipmentShopNotFoundException(Integer id) {
        super("Can`t find equipment shop with id: " + id);
    }
}
