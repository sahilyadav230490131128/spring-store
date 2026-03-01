package com.codewithmosh.store.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;

    @NotBlank(message = "name of the product should not be empty")
    @Size(max = 255)
    private String name;
    private String description;
    @Positive
    private BigDecimal price;
    private Byte category_id;
}
