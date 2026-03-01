package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotNull
    private Long productId;
    @NotNull(message = "Quantity is required")
    @Min(value = 1,message = "Quantity should be greater or equal to zero")
    @Max(value = 100,message = "Quantity should be less than or equal to 100")
    private Integer quantity;
}
