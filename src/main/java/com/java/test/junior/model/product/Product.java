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

    public Product(String name, double price, String description, Long userId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.userId = userId;
    }

    public Product(Long id, String name, double price, String description, Long userId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.userId = userId;
    }
}

