package com.example.bumacat.domain.product.exception;

public class UnauthorizedProductAccessException extends RuntimeException {
    private static final String MESSAGE = "Unauthorized access to product";
    
    private UnauthorizedProductAccessException() {
        super(MESSAGE);
    }
    
    public static UnauthorizedProductAccessException getInstance() {
        return new UnauthorizedProductAccessException();
    }
}
