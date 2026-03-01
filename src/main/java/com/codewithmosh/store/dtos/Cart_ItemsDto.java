package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cart_ItemsDto {

    private CartProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice ;
}
