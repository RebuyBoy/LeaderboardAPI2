package com.rebuy.service.exceptions;

public class ProviderNotFoundException extends RuntimeException{

    public ProviderNotFoundException(String message) {
        super(message);
    }

}
