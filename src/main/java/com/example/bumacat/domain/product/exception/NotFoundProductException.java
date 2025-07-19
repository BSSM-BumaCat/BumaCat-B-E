package com.example.bumacat.domain.product.exception;

public class NotFoundProductException extends RuntimeException {
    private static final String MESSAGE = "Product not found";
    
    private NotFoundProductException() {
        super(MESSAGE);
    }
    
    public static NotFoundProductException getInstance() {
        return new NotFoundProductException();
    }
}
