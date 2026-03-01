package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "order_items")
public class Order_Items {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column(name = "unit_price")
    private BigDecimal unit_price;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "total_price")
    private BigDecimal total_price;

    public Order_Items(Order order,Product product,Integer quantity)
    {
        this.order=order;
        this.product=product;
        this.quantity=quantity;
        this.unit_price=product.getPrice();
        this.total_price=unit_price.multiply(BigDecimal.valueOf(quantity));
    }
}
