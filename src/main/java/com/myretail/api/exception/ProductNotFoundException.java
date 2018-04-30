package com.myretail.api.exception;

public class ProductNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -5147727669620289856L;

	public ProductNotFoundException() {
        super();
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotFoundException(Throwable cause) {
        super(cause);
    }
}