/*
 * Copyright (c) 2013-2022 Global Database Ltd, All rights reserved.
 */

package com.java.test.junior.controller;

import com.java.test.junior.model.Product;
import com.java.test.junior.model.ProductDTO;
import com.java.test.junior.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/products")
@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Product createProduct(ProductDTO productDTO) {
//        return productService.createProduct(productDTO);
//    }

    @GetMapping
    public List<Product> getProducts(){
        return this.productService.getProducts();
    }

    @GetMapping("/{prodId}")
    public Product getProduct(@PathVariable Long prodId){
        return this.productService.getProduct(prodId);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product){
        return this.productService.addProduct(product);
    }

    @PutMapping("/{prodId}")
    public Product updateProduct(@PathVariable Long prodId, @RequestBody Product product){
        return this.productService.updateProduct(prodId, product);
    }

    @DeleteMapping("/{prodId}")
    public void deleteProduct(@PathVariable Long prodId){
        this.productService.deleteProduct(prodId);
    }
}