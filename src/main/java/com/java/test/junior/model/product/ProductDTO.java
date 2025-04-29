/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.model.product;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @DecimalMin(value = "0.0", message = "Price must be greater than zero")
    private Double price;
    private String description;
}