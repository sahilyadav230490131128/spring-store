package com.codewithmosh.store.mappers;

import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.Cart_ItemsDto;
import com.codewithmosh.store.entities.Cart_Items;
import org.hibernate.mapping.List;
import org.mapstruct.Mapper;
import com.codewithmosh.store.entities.Cart;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalPrice",expression = "java(cartItems.getTotalPrice())")
    Cart_ItemsDto toDto(Cart_Items cartItems);
    @Mapping(target = "cart_items",source = "items")
    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);
    Cart toEntity(CartDto cartDto);
    void addItems(CartDto cartDto, @MappingTarget Cart cart);

}
