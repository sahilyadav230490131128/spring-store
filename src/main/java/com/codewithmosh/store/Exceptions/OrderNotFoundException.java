package com.codewithmosh.store.Exceptions;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException()
    {
        super("Order not found");
    }
}
