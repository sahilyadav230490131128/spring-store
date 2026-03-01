package com.codewithmosh.store.Exceptions;

public class CartEmptyException extends RuntimeException {

    public CartEmptyException()
    {
        super("Cart is empty");
    }
}
