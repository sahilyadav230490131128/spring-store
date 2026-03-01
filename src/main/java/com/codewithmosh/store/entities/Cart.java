package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "cart")
public class Cart {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    @Column(name = "date_created",insertable = false,updatable = false)
    private LocalDate date_created;

    @OneToMany(mappedBy = "cart",cascade = {CascadeType.MERGE,CascadeType.REMOVE},fetch = FetchType.EAGER,orphanRemoval = true)
    private Set<Cart_Items> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice()
    {
        return items.stream().map(Cart_Items::getTotalPrice).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public Cart_Items getItems(Long productId)
    {
        return items.stream()
                .filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);
    }

    public Cart_Items addItems(Product product,Integer quantity)
    {
        var cartItem =   getItems(product.getId());
        if(cartItem!=null)
        {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        else
        {
            cartItem = new Cart_Items();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setCart(this);
            items.add(cartItem);
        }
        return cartItem;
    }

    public void removeItems(Long productId)
    {
        var cartItems = getItems(productId);
        if(cartItems!=null)
        {
            items.remove(cartItems);
            cartItems.setCart(null);
        }

    }

    public void removeAllItems()
    {
       items.clear();
    }
    public boolean isEmpty()
    {
        return items.isEmpty();
    }
}
