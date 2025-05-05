/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.model.product;

import lombok.*;

import java.time.LocalDateTime;

/**
 * @author dumitru.beselea
 * @version java-test-junior
 * @apiNote 08.12.2022
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(ProductDTO productDTO){
        this.name = productDTO.getName();
        this.price = productDTO.getPrice();
        this.description = productDTO.getDescription();
    }
    public Product(ProductDTO productDTO, Long userId){
        this(productDTO);
        this.userId = userId;
    }

    public Product(Long id, ProductDTO productDTO, Long userId){
        this(productDTO);
        this.id = id;
        this.userId = userId;
    }
}

