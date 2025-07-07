package com.ju.nextCart.exceptions;

import org.springframework.boot.info.JavaInfo;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
