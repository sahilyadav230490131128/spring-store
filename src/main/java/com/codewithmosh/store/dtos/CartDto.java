package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.Cart_Items;
import jakarta.persistence.SecondaryTable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
public class CartDto {
    private UUID id;
    private List<Cart_ItemsDto> cart_items ;
    private BigDecimal totalPrice;

}
