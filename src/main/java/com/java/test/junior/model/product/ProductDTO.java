/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.model.product;


import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;


@Data
public class ProductDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @DecimalMin(value = "0.0", message = "Price must be greater than zero")
    private Double price;
    private String description;
}