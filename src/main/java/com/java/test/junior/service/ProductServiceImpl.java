/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.service;

import com.java.test.junior.model.Product;
import com.java.test.junior.model.ProductDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    List<Product> products = new ArrayList<>(
            Arrays.asList(
            new Product(1L, "Wireless Mouse", 25.99, "Ergonomic wireless mouse", 101L, LocalDateTime.now().minusDays(5), LocalDateTime.now()),
            new Product(2L, "Mechanical Keyboard", 79.49, "RGB mechanical keyboard with blue switches", 102L, LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(2)),
            new Product(3L, "27-inch Monitor", 189.99, "Full HD LED monitor", 103L, LocalDateTime.now().minusWeeks(1), LocalDateTime.now().minusDays(3)),
            new Product(4L, "Laptop Stand", 34.95, "Adjustable aluminum laptop stand", 104L, LocalDateTime.now().minusDays(2), LocalDateTime.now()),
            new Product(5L, "USB-C Hub", 45.00, "7-in-1 USB-C hub with HDMI and Ethernet", 105L, LocalDateTime.now().minusDays(7), LocalDateTime.now().minusDays(1))
            )
    );

    @Override
    public Product createProduct(ProductDTO productDTO) {
        return null;
    }

    @Override
    public List<Product> getProducts(){
        return this.products;
    }

    @Override
    public Product getProduct(Long prodId){
        return products.stream().filter(p -> p.getId().equals(prodId)).findFirst().get();
    }
    @Override
    public Product addProduct(Product product){
        this.products.add(product);
        return product;
    }

    @Override
    public Product updateProduct(Long prodId, Product product) {
        int index = -1;
        for (int i = 0; i < products.size(); i++){
            if(products.get(i).getId().equals(prodId)){
                index = i;
            }
        }
        products.set(index,product);
        return product;
    }

    @Override
    public void deleteProduct(Long prodId){
        int index = -1;
        for (int i = 0; i < products.size(); i++){
            if(products.get(i).getId().equals(prodId)){
                index = i;
            }
        }
        products.remove(index);
    }
}
