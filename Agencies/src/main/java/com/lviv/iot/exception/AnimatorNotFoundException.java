package com.lviv.iot.exception;

public class AnimatorNotFoundException extends RuntimeException{
    public AnimatorNotFoundException(Integer id) {
        super("Can`t find animator with id: " + id);
    }
}
