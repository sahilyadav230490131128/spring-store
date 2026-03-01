package com.codewithmosh.store.Exceptions;
public class CartNotFoundException extends RuntimeException{
    public CartNotFoundException()
    {
        super("Cart not found");
    }
}
